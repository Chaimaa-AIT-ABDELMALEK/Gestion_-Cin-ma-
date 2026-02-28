[Setup]
AppName=MonApp
AppVersion=1.0
DefaultDirName={autopf}\MonApp
DefaultGroupName=MonApp
OutputDir=Output
OutputBaseFilename=MonApp_Setup
Compression=lzma
SolidCompression=yes

[Files]
Source: "MonApp.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "lib\*"; DestDir: "{app}\lib"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\MonApp"; Filename: "java"; Parameters: "-jar ""{app}\MonApp.jar"""
Name: "{commondesktop}\MonApp"; Filename: "java"; Parameters: "-jar ""{app}\MonApp.jar"""

[Run]
Filename: "java"; Parameters: "-jar ""{app}\MonApp.jar"""; Description: "Lancer MonApp"; Flags: nowait postinstall skipifsilent