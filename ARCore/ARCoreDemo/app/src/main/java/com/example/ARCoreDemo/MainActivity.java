/*
 * Copyright 2018 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ARCoreDemo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    private ViewRenderable mapRenderable, settingsRenderable;
    final int OPEN_REQUEST_CODE = 41;
    private float mapHeight = 1.0f;
    private ArFragment arFragment;
    private Anchor anchor;
    private AnchorNode settingsAnchorNode, mapAnchorNode;
    private boolean imageSet = false, settingsPlaced = false;
    private Uri heatmapUri;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    // CompletableFuture requires api level 24
    // FutureReturnValueIgnored is not valid
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        // Build the heatmap renderable from the view
        CompletableFuture<ViewRenderable> settingsMapStage =
                ViewRenderable.builder().setView(this, R.layout.settings).build();

        CompletableFuture.allOf(settingsMapStage)
                .handle(
                        (notUsed, throwable) -> {
                            if (throwable != null) {
                                DemoUtils.displayError(this, "Unable to load renderable", throwable);
                                return null;
                            }

                            try {
                                settingsRenderable = settingsMapStage.get();

                                // Everything finished loading successfully.
                            } catch (InterruptedException | ExecutionException ex) {
                                DemoUtils.displayError(this, "Unable to load renderable", ex);
                            }

                            return null;
                        });

        // Listener for attempts to place maps
        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    // Build the heatmap view
                    if(!settingsPlaced) {
                        // Build the heatmap renderable from the view

                        // Create the Anchor.
                        anchor = hitResult.createAnchor();
                        settingsAnchorNode = new AnchorNode(anchor);
                        settingsAnchorNode.setParent(arFragment.getArSceneView().getScene());

                        // Create the map and add it to the scene
                        Node settings = createSettings();
                        settingsAnchorNode.addChild(settings);
                        settingsPlaced = true;
                    }
                });
    }

    private Node createSettings() {
        Node base = new Node();

        Node settings = new Node();
        settings.setParent(base);
        settings.setRenderable(settingsRenderable);
        settings.setLocalPosition(new Vector3(0.0f, 0f, 0.0f));
        settings.setWorldRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), -90f));

        View settingsView = View.inflate(this, R.layout.settings, null);

        SeekBar mapHeightBar = (SeekBar) settingsView.findViewById(R.id.heightBar);
        mapHeightBar.setProgress((int) (mapHeight * 10));

        mapHeightBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mapHeight = (float) progress / 10.0f;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
        return base;
    }

    public void placeMap(View view) {
        // Remove the settings
        settingsAnchorNode.setParent(null);
        settingsPlaced = false;

        // Create the heatmap view
        View heatmapView = View.inflate(this, R.layout.heatmap, null);
        if (imageSet) {
            ImageView iv1 = (ImageView) heatmapView.findViewById(R.id.heatmapImage);
            iv1.setImageURI(heatmapUri);
        }

        // Build the heatmap renderable from the view
        CompletableFuture<ViewRenderable> heatmapMapStage =
                ViewRenderable.builder().setView(this, heatmapView).build();

        CompletableFuture.allOf(heatmapMapStage)
                .handle(
                        (notUsed, throwable) -> {
                            if (throwable != null) {
                                DemoUtils.displayError(this, "Unable to load renderable", throwable);
                                return null;
                            }

                            try {
                                mapRenderable = heatmapMapStage.get();

                                // Everything finished loading successfully.
                            } catch (InterruptedException | ExecutionException ex) {
                                DemoUtils.displayError(this, "Unable to load renderable", ex);
                            }

                            return null;
                        });

        // Create the Anchor.
        mapAnchorNode = new AnchorNode(anchor);
        mapAnchorNode.setParent(arFragment.getArSceneView().getScene());

        // Create the map and add it to the scene
        Node map = createMap();
        mapAnchorNode.addChild(map);
    }

    private Node createMap() {
        Node base = new Node();

        Node map = new Node();
        map.setParent(base);
        map.setRenderable(mapRenderable);
        map.setLocalPosition(new Vector3(0.0f, 0f, 0.0f));
        map.setWorldRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), -90f));
        map.setLocalScale(new Vector3(mapHeight, mapHeight, mapHeight));
        return base;
    }

    public void loadImage(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, OPEN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_REQUEST_CODE) {
                if (resultData != null) {
                    heatmapUri = resultData.getData();
                    imageSet = true;
                }
            }
        }
    }

    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     * <p>Finishes the activity if Sceneform can not run
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}
