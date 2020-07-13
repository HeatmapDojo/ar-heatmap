# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Fixed
- Changing the height setting now accurately updates the settings ViewRenderable and changes the height of rendered maps

## [0.2.0] - 2020-07-12
### Changed
- Tapping now adds a settings ViewRenderable instead of a map ViewRenderable to clarify how the settings affect maps
- Heatmap height and image settings are moved from the interface to a settings ViewRenderable
- Maps are placed upon pressing the "Generate" button on the settings ViewRenderable

## [0.1.0] - 2020-07-12
### Added
- [ARCoreDemo](ARCoreDemo), an android application to place heatmap images in AR space
- Plane detection
- Tap to add maps to the AR space
- Heatmap height setting which allows users to change the height of their maps
- Heatmap image setting which allows users to change the image for their maps
- [README](README.md) containing installation instructions
- [Screenshots](Screenshots) folder containing images of the app in-use, demonstrating the interface, custom images, and multiple maps.