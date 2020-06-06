$WorkspaceDir = Resolve-Path -Path ..
$OrderProjectDir = Join-Path $WorkspaceDir 'order-management'
$RoomProjectDir = Join-Path $WorkspaceDir 'room-management'
$UserProjctDir = Join-Path $WorkspaceDir 'user-management'
$GatewayProjectDir = Join-Path $WorkspaceDir 'gateway-management'

function Build-Project
{
  [CmdletBinding()]
  param (
    [Parameter(Mandatory)]
    [string] $Project,

    [Parameter()]
    [ValidateSet('maven', 'node')]
    [string] $Type = 'maven'
  )

  $ProjectName = Split-Path -Path $Project -Leaf
  Write-Host "building project $ProjectName ..."

  Set-Location $Project
  switch ($Type)
  {
    'maven' {
      if (Test-Path -Path .\target -PathType Container)
      {
        Remove-Item -Path .\target -Force -Recurse
      }

      try
      {
        mvn clean package -DskipTests
      }
      catch
      {
        Write-Host $Error
      }

      if (Test-Path -Path .\docker\app.jar)
      {
        Remove-Item -Path .\docker\app.jar
      }

      Move-Item -Path .\target\*.jar -Destination .\docker\app.jar
    }
    Default {
      throw 'invalid project type'
    }
  }

}

Build-Project -Project $OrderProjectDir -Type 'maven'
Build-Project -Project $RoomProjectDir -Type 'maven'
Build-Project -Project $UserProjctDir -Type 'maven'
Build-Project -Project $GatewayProjectDir -Type 'maven'


Set-Location "$WorkspaceDir/docker"

docker-compose up --build -d
