import urllib3 as u
import wget as w
import os
import sys
import zipfile
import datetime as dt
import pandas as pd

SUBMISSION_FILE='sub.txt'
RESOURCES_FOLDER='resources'

def getRegistrantIds(year, quarter, registrant):
    result = None
    filename = "{year}q{quarter}.zip".format(year=year, quarter=quarter)
    if os.path.exists(RESOURCES_FOLDER):
        folder = RESOURCES_FOLDER + "/" + "{year}q{quarter}".format(year=year, quarter=quarter)
    else:
        folder = getFile(year, quarter)
        if None == folder:
            print('something wrong')

    df = pd.DataFrame.from_csv(folder + '/' + SUBMISSION_FILE, sep='\t', header=0, index_col=None)
    result = df[df['name'] == registrant ]['adsh'][0]
    return result


def getFile(year, quarter):
    filename = "{year}q{quarter}.zip".format(year=year, quarter=quarter)
    url = "http://www.sec.gov/data/financial-statements/{filename}".format(filename=filename)
    file = os.getenv("TMP", "/tmp") + "/" +  filename
    folder = "{rootpath}/sec_importer_{ts}".format(rootpath=os.getenv("TMP", "/tmp"), ts=dt.datetime.now().strftime("%s"))
    try:
        w.download(url, file)
        zip_ref = zipfile.ZipFile(file, 'r')
        zip_ref.extractall(folder)
        zip_ref.close()
    except:
        print("Unexpected error:", sys.exc_info()[0])
        return None
    else:
        return folder



class PresentationOfStatements(object):

    def __init__(self, adsh_submissionId, report_reportGrouping, line
                 , stmt_statementLocation, inpth_parentheticallyPresented,
                 tag, version, plabel_preferredLabel):
        self.submissionId = adsh_submissionId
        self.reportGrouping = report_reportGrouping
        self.line = line
        self.statementLocation = stmt_statementLocation
        self.parentheticallyPresented = inpth_parentheticallyPresented
        self.tag = tag
        self.version = version
        self.preferredLabel = plabel_preferredLabel


class NumericData(object):

    def __init__(self, adsh_submissionId, tag, version, coreg_coRegistrant,
                 ddate_dataEndDate, qtrs_dataQuarters, uom_unitOfMeasure, value):
        self.submissionId = adsh_submissionId
        self.tag = tag
        self.version = version
        self.coRegistrant = coreg_coRegistrant
        self.dataEndDate = ddate_dataEndDate
        self.dataQuarters = qtrs_dataQuarters
        self.unitOfMeasure = uom_unitOfMeasure
        self.value = float(value)

class Submission(object):

    def __init__(self, adsh_id, clk_registrantId, name_registrant,
                 sic_stdIndustrialClassification, countryInc_country, afs_filerStatus
                 , fye_fiscalYearEnd, form_submissionType, period_balanceSheetDate
                 , fy_fiscalYearFocus, fp_fiscalPeriodFocus):
        self.id = adsh_id
        self.registrantId = clk_registrantId
        self.registrant = name_registrant
        self.stdIndustrialClassification = sic_stdIndustrialClassification
        self.country = countryInc_country
        self.filerStatus = afs_filerStatus
        self.fiscalYearEnd = fye_fiscalYearEnd
        self.type = form_submissionType
        self.balanceSheetDate = period_balanceSheetDate
        self.fiscalYearFocus = fy_fiscalYearFocus
        self.fiscalPeriodFocus = fp_fiscalPeriodFocus


#    def roll(self):
#        return randint(1, self.n_sides)