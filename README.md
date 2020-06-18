Augmented and virtual reality technology for biomedical data visualization
Over the last few decades, there has been a paradigm shift from viewing data as static plots to dynamic interactive 3D visualizations, which enable deeper insights into the interplay of different parameters. However, 2D screens/monitors are inherently limiting for exploring 3D graphics of large-scale virtual objects (e.g., complex networks in 3D space). For instance, when a network diagram gets very large (e.g., 103 -106 nodes), visualizing its 3D structure on a 2D desktop monitor becomes not only cumbersome from a user experience (UX) perspective, but also fundamentally constraining from a user interface (UI) perspective (because zooming loses visual context of the surrounding nodes environment -- i.e., the tradeoff of “the more you zoom, the less you see”). As such, developing innovative biomedical technology that can enable room-scale virtual models at the level of 3D manipulatable projections would represent a significant technical advance over the state of the art in visual data exploration. Recently, 2D visualization helper tools such as scalable insets (Lekschas et al., 2019) have been developed to address these locality challenges, but these tools still remain relatively unintuitive compared to the potential 3D immersive experience afforded by augmented reality (AR). In general, 3D user interfaces and user experiences are largely an unexplored frontier, prompting further research at the multidisciplinary interface of human-computer interaction (HCI) and AR. Since AR is about the intersection of digital and physical interfaces, we believe that long-standing visualization challenges such as these can be adequately addressed with spatial computing (i.e., augmented reality methodologies). Therefore, the primary goal and objective of this project is to facilitate the development of next-generation AR tools for large-scale biomedical visualization in three major areas of focus: complex networks, interactive heatmaps, and DNA structure (nucleosomes). Since better visualization methods directly facilitate better data exploration and discovery and, given the historical usage and broad popularity of these types of graphics in fields ranging from single-cell transcriptomics to quantitative proteomics, we anticipate the primary utility of our proposed technology will be to allow biomedical and clinical investigators to perform better tests of novel research hypotheses across multiple biological systems and diseases. 
Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.
Prerequisites
This project will be utilizing Microsoft Hololens as well as IOS ARKit.

Microsoft offers two primary options for Hololens. The first option is purchasing Hololens with Dynamics 365 Remote Assist, which allows for hands-free sharing of visual data. The second option is purchasing the Hololens by itself, which costs about $3500. 

Installation
Hololens:
Microsoft Hololens is an extension of the field of AR, and allows one to create digital holograms that can be interacted with in the real world. Holograms are objects that are made out of light and sound, and can both be interacted with via one’s gaze, gestures, and voice commands as well as interact with the environment around it. 

Currently, Microsoft Hololens can be implemented in four different ways: Through Unity, Unreal, JavaScript, and Native.

The quickest development path for a mixed reality app would be through Unity, which is a real-time engine that can be used to develop three-dimensional, VR, and AR simulations and experiences. Microsoft currently has an open source cross-development kit created specifically for Unity called the MRTK v2 with Unity, which is straightforward to set up using their MRTK tutorial. This allows for an automatic setup of your project, as well as options to quickly add mixed reality features to speed up development. For instance, adding support for Windows Mixed Reality features such as spatial stages, gestures, motion controllers, or voice input can be easily implemented using APIs that are already built into Unity. Unity also options to set different tracking room types. To implement the kind of room-scale experience that this project is looking to achieve, Unity’s tracking room type can be set to a standing-scale or room-scale experience. Once a Unity project is properly set-up and ready for implementation, the next step would be to export and build a Unity Visual Studio solution, which Microsoft also offers step-by-step tutorials for. Finally, once a VS studio is ready for deployment, the application can be then run via a Hololens or Windows MR headset, Hololens emulator, or Windows MR headset simulator. 

Unity also offers documentation for Windows MR functionality alongside the Unity editor. For clarification on Unity, it offers a comprehensive manual on anything ranging from basic to advanced techniques on using the platform. Additionally, Unity also offers documentation on the scripting of Unity itself, which is also included on its Unity Editor. 

Mobile IOS Development Using IOS ARKit:

Apple’s ARKit is a library that is included in their Software Development Kit (SDK) which allows IOS developers to create augmented reality apps and take advantage of the different hardware and software capabilities of IOS devices. ARKit can be directly implemented through Apple’s native XCode.

To start a new ARKit project, Apple offers a pre-written code template called their Apple ARKit sample which provides a few essential code blocks and is useful for plane detection. Additionally, Apple also offers a native 3D engine called iOS SceneKit. Like most other built-in api’s that Apple has, SceneKit can be easily implemented via a few lines of code that can be found by doing a quick scan of Apple’s documentation. However, a powerful way to take advantage of Apple’s ARKit is through implementation via Unity. 

Unity launcher can be accessed through Apple’s XCode, which has a Unity ARKit plugin built into its asset store. After being downloaded and imported into a project, Unity can be quickly deployed and run via a series of step-by-step tutorials provided by either Unity’s tutorials on the subject or a quick Google search on the subject. 

Unity Build Settings:
(Build Settings options in the File menu at the top)
Step 1: To change the specified platform to iOS, click File > Build Settings > select iOS. Check “Development Build” in that window as well, and check “ARKitScene” in the scenes section of the build options.
Step 2: Back to File > Build Settings. Click on “Player Settings,” then “Other Settings (Inspector).” Enter a unique “Bundle Identifier.” Match it with your Company and Product name. Double check that the camera usage description is not blank, then set the target minimum iOS version to 11.0.
Step 3: Go back into Build Settings and click “Build and Run.” 
Final Steps:
Step 1: In the top left, click Build-time, then select the folder Unity-iPhone and look for the "signing" section in the middle pane. Click on "Team" to access the dropdown menu, and select your Apple developer account. If you don't have that option, add one.
Step 2: Underneath that section, in Deployment, select your Apple device (iPhone or iPad). Head to the Product menu and click "Build." After the build is complete, head back to the Product menu and click "Run."
Upon finishing these steps, you should have set up your apple ARKit to be able to start developing mobile apps. After this is accomplished, one can adjust code and add in additional documentation from Apple’s native api’s and libraries to make a unique AR app to their liking.
Screenshot of a HeatmapDemo in Unity
Built With
Hololens
ARKit
Contributing
Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests to us.
Authors
Bohdan Khomtchouk - Initial work 
See also the list of contributors who participated in this project.
License
This project is licensed under the UChicago License - see the LICENSE.md file for details


