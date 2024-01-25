.
└── STACompanion
├── app
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   ├── release
│   │   ├── app-release.aab
│   │   ├── app-release.apk
│   │   └── output-metadata.json
│   ├── src
│   │   ├── androidTest
│   │   │   └── java
│   │   │       └── io
│   │   │           └── github
│   │   │               └── k3ssdev
│   │   │                   └── stacompanion
│   │   │                       └── ExampleInstrumentedTest.java
│   │   ├── google-services.json
│   │   ├── main
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── assets
│   │   │   │   ├── STA_personaje.pdf
│   │   │   │   └── Stardate81316-aolE.ttf
│   │   │   ├── ic_launcher-playstore.png
│   │   │   ├── java
│   │   │   │   └── io
│   │   │   │       └── github
│   │   │   │           └── k3ssdev
│   │   │   │               └── stacompanion
│   │   │   │                   ├── data
│   │   │   │                   │   ├── CharacterFragmentAdapter.java
│   │   │   │                   │   ├── CharacterSheet.java
│   │   │   │                   │   ├── SheetSettings.java
│   │   │   │                   │   └── Starship.java
│   │   │   │                   ├── LegalActivity.java
│   │   │   │                   ├── LoginActivity.java
│   │   │   │                   ├── MainActivity.java
│   │   │   │                   ├── ui
│   │   │   │                   │   ├── characters
│   │   │   │                   │   │   ├── CharactersFragment.java
│   │   │   │                   │   │   ├── charactersheet
│   │   │   │                   │   │   │   ├── CharacterSheetFragment.java
│   │   │   │                   │   │   │   ├── DataTabFragment.java
│   │   │   │                   │   │   │   ├── OthersFragment.java
│   │   │   │                   │   │   │   ├── SkillsFragment.java
│   │   │   │                   │   │   │   └── StatusFragment.java
│   │   │   │                   │   │   ├── CharacterSheetEditActivity.java
│   │   │   │                   │   │   └── CharacterSheetViewModel.java
│   │   │   │                   │   ├── dice
│   │   │   │                   │   │   ├── DiceFragment.java
│   │   │   │                   │   │   ├── DiceResultAdapter.java
│   │   │   │                   │   │   ├── DiceResult.java
│   │   │   │                   │   │   ├── DiceViewModel.java
│   │   │   │                   │   │   └── SpaceItemDecoration.java
│   │   │   │                   │   └── settings
│   │   │   │                   │       ├── SettingsFragment.java
│   │   │   │                   │       └── SettingsViewModel.java
│   │   │   │                   └── util
│   │   │   │                       └── PdfUtil.java
│   │   │   └── res
│   │   │       ├── anim
│   │   │       │   └── rotate_animation.xml
│   │   │       ├── drawable
│   │   │       │   ├── baseline_add_24.xml
│   │   │       │   ├── baseline_casino_24.xml
│   │   │       │   ├── baseline_contact_page_24.xml
│   │   │       │   ├── baseline_delete_forever_24.xml
│   │   │       │   ├── baseline_edit_24.xml
│   │   │       │   ├── baseline_manage_accounts_24.xml
│   │   │       │   ├── baseline_picture_as_pdf_24.xml
│   │   │       │   ├── baseline_replay_24.xml
│   │   │       │   ├── baseline_restart_alt_24.xml
│   │   │       │   ├── baseline_sort_24.xml
│   │   │       │   ├── custom_edittext.xml
│   │   │       │   ├── dice20_01.xml
│   │   │       │   ├── dice20_02.xml
│   │   │       │   ├── dice20_03.xml
│   │   │       │   ├── dice20_04.xml
│   │   │       │   ├── dice20_05.xml
│   │   │       │   ├── dice20_06.xml
│   │   │       │   ├── dice20_07.xml
│   │   │       │   ├── dice20_08.xml
│   │   │       │   ├── dice20_09.xml
│   │   │       │   ├── dice20_10.xml
│   │   │       │   ├── dice20_11.xml
│   │   │       │   ├── dice20_12.xml
│   │   │       │   ├── dice20_13.xml
│   │   │       │   ├── dice20_14.xml
│   │   │       │   ├── dice20_15.xml
│   │   │       │   ├── dice20_16.xml
│   │   │       │   ├── dice20_17.xml
│   │   │       │   ├── dice20_18.xml
│   │   │       │   ├── dice20_19.xml
│   │   │       │   ├── dice20_20.xml
│   │   │       │   ├── dice6_fail.xml
│   │   │       │   ├── dice6_special.xml
│   │   │       │   ├── dice6_success_double.xml
│   │   │       │   ├── dice6_success.xml
│   │   │       │   ├── google_icon.png
│   │   │       │   ├── ic_baseline_person_24.xml
│   │   │       │   ├── ic_dashboard_black_24dp.xml
│   │   │       │   ├── ic_email.xml
│   │   │       │   ├── ic_google.xml
│   │   │       │   ├── ic_home_black_24dp.xml
│   │   │       │   ├── ic_launcher_background.xml
│   │   │       │   ├── ic_launcher_foreground.xml
│   │   │       │   ├── ic_notifications_black_24dp.xml
│   │   │       │   ├── ic_search_black_24dp.xml
│   │   │       │   ├── lock_76.xml
│   │   │       │   ├── outline_file_save_24.xml
│   │   │       │   └── rounded_button.xml
│   │   │       ├── drawable-night
│   │   │       │   ├── baseline_add_24.xml
│   │   │       │   ├── baseline_casino_24.xml
│   │   │       │   ├── baseline_edit_24.xml
│   │   │       │   ├── baseline_replay_24.xml
│   │   │       │   ├── dice20_01.xml
│   │   │       │   ├── dice20_02.xml
│   │   │       │   ├── dice20_03.xml
│   │   │       │   ├── dice20_04.xml
│   │   │       │   ├── dice20_05.xml
│   │   │       │   ├── dice20_06.xml
│   │   │       │   ├── dice20_07.xml
│   │   │       │   ├── dice20_08.xml
│   │   │       │   ├── dice20_09.xml
│   │   │       │   ├── dice20_10.xml
│   │   │       │   ├── dice20_11.xml
│   │   │       │   ├── dice20_12.xml
│   │   │       │   ├── dice20_13.xml
│   │   │       │   ├── dice20_14.xml
│   │   │       │   ├── dice20_15.xml
│   │   │       │   ├── dice20_16.xml
│   │   │       │   ├── dice20_17.xml
│   │   │       │   ├── dice20_18.xml
│   │   │       │   ├── dice20_19.xml
│   │   │       │   ├── dice20_20.xml
│   │   │       │   ├── dice6_fail.xml
│   │   │       │   ├── dice6_special.xml
│   │   │       │   ├── dice6_success_double.xml
│   │   │       │   ├── dice6_success.xml
│   │   │       │   ├── ic_baseline_person_24.xml
│   │   │       │   ├── ic_email.xml
│   │   │       │   ├── ic_search_black_24dp.xml
│   │   │       │   ├── lock_76.xml
│   │   │       │   └── outline_file_save_24.xml
│   │   │       ├── layout
│   │   │       │   ├── activity_character_sheet_edit.xml
│   │   │       │   ├── activity_legal.xml
│   │   │       │   ├── activity_login.xml
│   │   │       │   ├── activity_main.xml
│   │   │       │   ├── character_sheet_item.xml
│   │   │       │   ├── dice_result_item.xml
│   │   │       │   ├── fragment_account.xml
│   │   │       │   ├── fragment_character_sheet.xml
│   │   │       │   ├── fragment_character.xml
│   │   │       │   ├── fragment_dice.xml
│   │   │       │   ├── fragment_tab_sheet_data.xml
│   │   │       │   ├── fragment_tab_sheet_others.xml
│   │   │       │   ├── fragment_tab_sheet_skills.xml
│   │   │       │   └── fragment_tab_sheet_status.xml
│   │   │       ├── menu
│   │   │       │   ├── bottom_nav_menu.xml
│   │   │       │   ├── character_sheet_toolbar_menu_edit.xml
│   │   │       │   ├── character_sheet_toolbar_menu.xml
│   │   │       │   ├── dice_menu.xml
│   │   │       │   └── toolbar_menu.xml
│   │   │       ├── mipmap-anydpi-v26
│   │   │       │   ├── ic_launcher_round.xml
│   │   │       │   └── ic_launcher.xml
│   │   │       ├── mipmap-hdpi
│   │   │       │   ├── ic_launcher_foreground.webp
│   │   │       │   ├── ic_launcher_round.webp
│   │   │       │   ├── ic_launcher.webp
│   │   │       │   ├── stacompanion_blue.png
│   │   │       │   ├── stacompanion_logo2.png
│   │   │       │   ├── stacompanion_logo.png
│   │   │       │   ├── stacompanion_red.png
│   │   │       │   └── stacompanion_yellow.png
│   │   │       ├── mipmap-mdpi
│   │   │       │   ├── ic_launcher_foreground.webp
│   │   │       │   ├── ic_launcher_round.webp
│   │   │       │   └── ic_launcher.webp
│   │   │       ├── mipmap-xhdpi
│   │   │       │   ├── ic_launcher_foreground.webp
│   │   │       │   ├── ic_launcher_round.webp
│   │   │       │   └── ic_launcher.webp
│   │   │       ├── mipmap-xxhdpi
│   │   │       │   ├── ic_launcher_foreground.webp
│   │   │       │   ├── ic_launcher_round.webp
│   │   │       │   └── ic_launcher.webp
│   │   │       ├── mipmap-xxxhdpi
│   │   │       │   ├── ic_launcher_foreground.webp
│   │   │       │   ├── ic_launcher_round.webp
│   │   │       │   └── ic_launcher.webp
│   │   │       ├── navigation
│   │   │       │   └── mobile_navigation.xml
│   │   │       ├── values
│   │   │       │   ├── colors.xml
│   │   │       │   ├── dimens.xml
│   │   │       │   ├── keys.xml
│   │   │       │   ├── strings.xml
│   │   │       │   ├── styles.xml
│   │   │       │   └── themes.xml
│   │   │       ├── values-es
│   │   │       │   └── strings.xml
│   │   │       ├── values-night
│   │   │       │   ├── keys.xml
│   │   │       │   ├── strings.xml
│   │   │       │   └── themes.xml
│   │   │       ├── values-v26
│   │   │       │   ├── strings.xml
│   │   │       │   └── styles.xml
│   │   │       └── xml
│   │   │           ├── backup_rules.xml
│   │   │           ├── data_extraction_rules.xml
│   │   │           └── file_paths.xml
│   │   └── test
│   │       └── java
│   │           └── io
│   │               └── github
│   │                   └── k3ssdev
│   │                       └── stacompanion
│   │                           └── ExampleUnitTest.java
│   └── stacompanion-a1286-default-rtdb-export.json
├── build.gradle.kts
│   └── wrapper
│       │          
│       │           
│       │           
│       │          
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradle.properties
├── gradlew
├── gradlew.bat
├── images
│   ├── stacompanion_blue.png
│   ├── stacompanion_blue_rounded.png
│   ├── stacompanion_screenshot_1.jpg
│   ├── stacompanion_screenshot_2.jpg
│   └── stacompanion_screenshot_3.jpg
├── LICENSE
├── local.properties
├── README.MD
├── settings.gradle.kts
└── sta_tree.md