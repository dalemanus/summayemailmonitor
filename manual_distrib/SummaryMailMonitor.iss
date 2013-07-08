; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Summary Email Monitor"
#define MyAppVersion "0.5"
#define MyAppPublisher "Thomson Reuters"
#define MyAppURL "http://www.thomsonreuters.com/"
#define MyAppExeName "monitor.bat"
;#define MyAppExeName "java -cp . insider.mail.SummaryMailMonitor"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{0B2DD66D-EB28-4CD7-B711-AAD66E2AB865}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
OutputDir=C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\
OutputBaseFilename=monitor_setup
SetupIconFile=C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\monitor.ico
;UninstallIconFile =
Password=Insid3r
Compression=lzma
SolidCompression=yes



[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked
Name: "quicklaunchicon"; Description: "{cm:CreateQuickLaunchIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked; OnlyBelowVersion: 0,6.1

[Dirs]
Name: "{app}\insider\mail"
Name: "{app}\insider\utils"
Name: "{app}\log"

[Files]
;Source: "C:\Program Files\Summary Email Monitor\java insider.mail.SummaryMailMonitor"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\log4j.properties"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\smm_config.props"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\Install1.ico"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\monitor.bat"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\lib\log4j-1.2.17.jar"; DestDir: "{app}\lib"; Flags: ignoreversion
Source: "C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\insider\mail\AlertProcessor.class"; DestDir: "{app}\insider\mail"; Flags: ignoreversion
Source: "C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\insider\mail\SummaryMailMonitor$1.class"; DestDir: "{app}\insider\mail"; Flags: ignoreversion
Source: "C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\insider\mail\SummaryMailMonitor.class"; DestDir: "{app}\insider\mail"; Flags: ignoreversion
Source: "C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\insider\utils\GeneralUtils.class"; DestDir: "{app}\insider\utils"; Flags: ignoreversion
Source: "C:\Documents and Settings\dale.macdonald\workspace\GitHub_SummaryMailMonitor\manual_distrib\insider\utils\UrlUtils.class"; DestDir: "{app}\insider\utils"; Flags: ignoreversion
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName} {code:GetProxyInfo|ProxyServer} {code:GetProxyInfo|ProxyPort}"; IconFilename: "{app}\Install1.ico" 
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\Install1.ico"; Tasks: desktopicon; Flags: 
Name: "{userappdata}\Microsoft\Internet Explorer\Quick Launch\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\Install1.ico"; Tasks: quicklaunchicon

[Run]
;Filename: "java {app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: shellexec postinstall skipifsilent

[Code]
var
  ProxyInfoPage: TInputQueryWizardPage;

procedure InitializeWizard;
begin
  { Create the pages }

  ProxyInfoPage := CreateInputQueryPage(wpWelcome,
    'ProxyInformation', 'Custom Proxy Details ?',
    'Please specify your proxy address  and the proxy port then click Next. (Type "null" for no proxy)');

  ProxyInfoPage.Add('ProxyServer:', False);
  ProxyInfoPage.Add('ProxyPort:', False);

  { Default values }
  ProxyInfoPage.Values[0] := 'mhsproxy.datastream.com';
  ProxyInfoPage.Values[1] := '80';

end;

function GetProxyInfo(Param: String): String;
begin
  { Return a user value }

  if Param = 'ProxyServer' then
    Result := ProxyInfoPage.Values[0]
  else if Param = 'ProxyPort' then
    Result := ProxyInfoPage.Values[1];
end;










