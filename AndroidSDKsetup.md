# Introduction #

These steps should be relatively the same for both PC and Mac OS X.
Follow these steps first: [Setup Instructions for the OUYA ODK](https://devs.ouya.tv/developers/docs/setup) (skip Step 5 on Mac and stop at Step 7 on Windows). On Mac Step 3 and Windows Step 2, also install Android 4.2.2 API Level 17

# Installation #
  * Start here:  http://developer.android.com/index.html

  * Eclipse: Get SVN tool called 'subversive'
    * Click on Eclipse->Help->Install New Software->Add
    * Name: "Update Site"
    * Location: http://community.polarion.com/projects/subversive/download/eclipse/3.0/juno-sr2-site/
    * Select Update Site from the 'Work with' dropdown
    * Select and install the following:
      * Subversive SVN Team Provider
      * SVNKit 1.3.8 Implementation
  * Eclipse: Download the repository code ... somehow (fill in this step)
    * URL: https://cbpoc.googlecode.com/svn
    * Authentication: Google username ex. cmanbrown@gmail.com
    * Authentication: Enter your generated [googlecode.com](https://code.google.com/hosting/settings) password.
  * Eclipse: Add an Emulator virtual device
    * Click on Eclipse->Window->Android Virtual Device Manager->New
    * AVN Name: Anything memorable. Doesn't matter
    * Device: Nexus 4
    * Target: Android 4.2.2 - API Level 17
    * Click OK
  * Eclipse: Speed up your emulator with these directions:
    * [Speeding Up the Android Emulator on Intel Architecture](http://software.intel.com/en-us/articles/speeding-up-the-android-emulator-on-intel-architecture)
    * Tip: When the article says to enable GPU emulation, they mean selecting the **Use Host GPU** option.