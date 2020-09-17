using System.Collections;
using System.Diagnostics;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEditor;

public class HeatmapUI : MonoBehaviour
{
    public InputField widthInput, heightInput, TileRatioXInput, TileRatioYInput, ColumnsInput, RowsInput, ColorschemeInput, ImageInput;
    public Button FilenameInput, Generate;
    public Text file, filePlaceholder;
    public GameObject heatmap;
    public Canvas menu;

    private string numbers = "0123456789";
    private Process heatmapGenerator;
    private ProcessStartInfo psInfo = new ProcessStartInfo();
    private Sprite hmImport = null;
    private string generatorLocation, outputLocation, heatmapLocation;


    // Start is called before the first frame update
    void Start()
    {
        generatorLocation = Application.dataPath + "../../heatmap_image_generator/generators/heatmap_gen";
        outputLocation = Application.dataPath + "/Resources";

        //Show only the placeholder for the button so it acts like a text field
        file.gameObject.SetActive(false);
        //Don't show the heatmap 
        heatmap.gameObject.SetActive(false);

        //Initialize the buttons
        FilenameInput.onClick.AddListener(() => ButtonCall(FilenameInput));
        Generate.onClick.AddListener(() => ButtonCall(Generate));
    }

    public void Update()
    {

        // Making sure that there can only be numbers for the width, height, etc. inputs
        widthInput.onValidateInput = (string text, int charIndex, char newChar) =>
        {
            return ValidateInput(numbers, newChar);
        };
        heightInput.onValidateInput = (string text, int charIndex, char newChar) =>
        {
            return ValidateInput(numbers, newChar);
        };
        TileRatioXInput.onValidateInput = (string text, int charIndex, char newChar) =>
        {
            return ValidateInput(numbers, newChar);
        };
        TileRatioYInput.onValidateInput = (string text, int charIndex, char newChar) =>
        {
            return ValidateInput(numbers, newChar);
        };
        ColumnsInput.onValidateInput = (string text, int charIndex, char newChar) =>
        {
            return ValidateInput(numbers, newChar);
        };
        RowsInput.onValidateInput = (string text, int charIndex, char newChar) =>
        {
            return ValidateInput(numbers, newChar);
        };

    }

    private char ValidateInput(string validChars, char inputChar)
    {
        if (validChars.IndexOf(inputChar) != -1)
        {
            return inputChar;
        }
        else
        {
            return '\0';
        }
    }

    private void ButtonCall(Button b)
    {
        //Letting the user select the location of the data file
        if (b == FilenameInput)
        {
            file.text = EditorUtility.OpenFilePanel("Choose Data File", "", "csv");
            filePlaceholder.gameObject.SetActive(false);
            file.gameObject.SetActive(true);
        }

        if (b == Generate)
        {
            Heatmap();
            ShowHeatmap();
        }
    }

    //Generate the heatmap based on the inputs
    private void Heatmap()
    {
        heatmapLocation = outputLocation + "/" + ImageInput.text + ".png";
        psInfo.FileName = generatorLocation;
        psInfo.Arguments = widthInput.text + " " + heightInput.text + " " +
            TileRatioXInput.text + " " + TileRatioYInput.text + " " + ColumnsInput.text + " " +
            RowsInput.text + " " + file.text + " " + ColorschemeInput.text + " > " + heatmapLocation;
        heatmapGenerator = new Process();
        heatmapGenerator.StartInfo = psInfo;
        heatmapGenerator.Start();

        //heatmapGenerator.WaitForExit();
    }

    //Display the heatmap generated
    private void ShowHeatmap()
    {
        AssetDatabase.Refresh();
        AssetDatabase.ImportAsset("Assets/Resources/" + ImageInput.text + ".png");
        TextureImporter importer = AssetImporter.GetAtPath("Assets/Resources/" + ImageInput.text + ".png") as TextureImporter;
        importer.textureType = TextureImporterType.Sprite;
        AssetDatabase.WriteImportSettingsIfDirty("Assets/Resources/" + ImageInput.text + ".png");
        hmImport = Resources.Load<Sprite>(ImageInput.text);
        if (hmImport != null)
        {
            heatmap.GetComponent<Image>().sprite = hmImport;
            menu.gameObject.SetActive(false);
            heatmap.gameObject.SetActive(true);
        }
        else
        {
            UnityEngine.Debug.LogError("Could not load heatmap.\n");
        }
    }
}