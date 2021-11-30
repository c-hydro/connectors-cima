"""
Library Features:

Name:          Lib_Op_System_Apps
Author(s):     Fabio Delogu (fabio.delogu@cimafoundation.org)
Date:          '20171115'
Version:       '2.0.4'
"""
#######################################################################################

# -------------------------------------------------------------------------------------
# Method to get module object using a function name
def getModule2FxObj(oModule, sFunctionName):

    if hasattr(oModule, sFunctionName):
        oFunctionObj = getattr(oModule, sFunctionName)
    else:
        oFunctionObj = None

    return oFunctionObj
# -------------------------------------------------------------------------------------

# -------------------------------------------------------------------------------------
# Method to get function object using module filename and function name
def getFileName2FxObj(sFileName, sFunctionName):

    from os.path import exists
    from imp import load_source

    if exists(sFileName):
        oModuleObj = load_source(sFunctionName, sFileName)

        if hasattr(oModuleObj, sFunctionName):
            oFunctionObj = getattr(oModuleObj, sFunctionName)
        else:
            oFunctionObj = None
    else:
        oFunctionObj = None

    return oFunctionObj

# -------------------------------------------------------------------------------------

# -------------------------------------------------------------------------------------
# Method to move file from source to destination
def moveFile(sFileSource, sFileDestination):

    from os.path import exists, dirname
    from os import remove, makedirs
    from shutil import move

    sPathDestination = dirname(sFileDestination)

    if not exists(sPathDestination):
        makedirs(sPathDestination)
    else:
        pass

    if exists(sFileSource):
        if not sFileSource == sFileDestination:
            if exists(sFileDestination):
                remove(sFileDestination)
            move(sFileSource, sFileDestination)
        else:
            pass
    else:
        pass

# -------------------------------------------------------------------------------------

# -------------------------------------------------------------------------------------
# Method to copy file from source to destination
def copyFile(sFileSource, sFileDestination):

    from os.path import exists, dirname
    from os import remove, makedirs
    from shutil import copy2

    sPathDestination = dirname(sFileDestination)

    if not exists(sPathDestination):
        makedirs(sPathDestination)
    else:
        pass

    if exists(sFileSource):
        if not sFileSource == sFileDestination:
            if exists(sFileDestination):
                remove(sFileDestination)
            copy2(sFileSource, sFileDestination)
        else:
            pass
    else:
        pass

# -------------------------------------------------------------------------------------

# -------------------------------------------------------------------------------------
# Method to get regular expression from file
def getFileNameRegExp(a1oFileName, oFilePattern=r'\d{4}\d{2}\d{2}\d{2}\d{2}', oFileFilter=r'[^a-zA-Z0-9-]'):

    import re

    # Cycle(s) on filename(s)
    a1sRegExp = []
    for sFileName in a1oFileName:

        # Match date in filename(s)
        oMatch_Pattern = re.search(oFilePattern, sFileName)

        # Get date of filename(s)
        oMatch_Filter = re.compile(oFileFilter)
        sRegExp = oMatch_Pattern.group()
        sRegExp = oMatch_Filter.sub("", sRegExp)
        a1sRegExp.append(sRegExp)

    return a1sRegExp
# -------------------------------------------------------------------------------------

# -------------------------------------------------------------------------------------
# Method to create filename patterns
def createFileNamePattern(sFileName, oFileTags={}):
    for sFileKey, sFileTag in oFileTags.iteritems():
        sFileName_Upd = sFileName.replace(sFileKey, sFileTag)
    return sFileName_Upd

# -------------------------------------------------------------------------------------

# -------------------------------------------------------------------------------------
# Method to select files in a given folder
def selectFileName(sPathName='', sFilePattern=None):
    from os.path import join, isfile

    if not sFilePattern:
        from os import listdir
        a1sFileName = [sFileName for sFileName in listdir(sPathName) if isfile(join(sPathName, sFileName))]
    else:
        import glob
        a1sFileName = glob.glob(join(sPathName, sFilePattern))
    return a1sFileName

# -------------------------------------------------------------------------------------

# -------------------------------------------------------------------------------------
# Method to create folder (and check if folder exists)
def createFolder(sPathName='', sPathDeep=None):

    from os import makedirs
    from os.path import exists

    if sPathName != '':
        if sPathDeep:
            sPathNameSel = sPathName.split(sPathDeep)[0]
        else:
            sPathNameSel = sPathName

        if not exists(sPathNameSel):
            makedirs(sPathNameSel)
        else:
            pass
    else:
        pass
# -------------------------------------------------------------------------------------

# --------------------------------------------------------------------------------
# Method to define dynamic folder name
def defineFolder(sFolderName='', oFileNameDict=None, sPathDelimiter='$'):

    from os.path import join

    if sFolderName != '':
        if not oFileNameDict:
            pass
        elif oFileNameDict:
            for sKey, oValue in oFileNameDict.items():

                if isinstance(oValue, str):
                    sFolderName = sFolderName.replace(sKey, oValue)

        # Split string if delimiter found is found in a path
        if sPathDelimiter in sFolderName:
            [sFolderRoot, sFolderUndefined] = sFolderName.split(sPathDelimiter)
        else:
            sFolderRoot = sFolderName
            sFolderUndefined = None

        # Create folder on disk
        createFolder(sFolderRoot)

        if sFolderUndefined is not None:
            sFolderName = join(sFolderRoot, sPathDelimiter + sFolderUndefined)
        else:
            sFolderName = sFolderRoot
    else:
        pass

    return sFolderName

# --------------------------------------------------------------------------------

# --------------------------------------------------------------------------------
# Method to define check file availability name
def checkFileName(sFileName):

    from os.path import isfile

    if isfile(sFileName):
        bFileAvailability = True
    else:
        bFileAvailability = False

    return bFileAvailability
# --------------------------------------------------------------------------------

# --------------------------------------------------------------------------------
# Method to define dynamic filename
def defineFileName(sFileName='', oFileNameDict=None):
    if sFileName != '':
        if not oFileNameDict:
            pass
        elif oFileNameDict:
            for sKey, sValue in oFileNameDict.items():
                sFileName = sFileName.replace(sKey, sValue)
    else:
        pass

    return sFileName

# --------------------------------------------------------------------------------

# --------------------------------------------------------------------------------
#  Method to define file extension
def defineFileExt(sFileName=''):

    from os.path import splitext
    try:
        [sFileRoot, sFileExt] = splitext(sFileName)
    except:
        sFileRoot = splitext(sFileName)
        sFileExt = ''

    sFileExt = sFileExt.replace('.', '')

    return sFileExt
# --------------------------------------------------------------------------------

# --------------------------------------------------------------------------------
# Method to delete file
def deleteFileName(sFileName=''):

    from os import remove
    from os.path import exists

    if sFileName != '':
        # Delete file if exists
        if exists(sFileName):
            remove(sFileName)
        else:
            pass
    else:
        pass
# --------------------------------------------------------------------------------
