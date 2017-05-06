import unittest
import sec_importer as s

class SecImporterTestCase(unittest.TestCase):
    """tests for formatName"""
    def setUp(self):
        print('setting up')

    def test_class(self):
        o = s.Submission('0001564590-17-003118', 1318605, 'TESLA, INC.',
                       3711, 'US', '1-LAF',
                       '1231', '10-K', '20161231',
                       2016, 'FY')
        self.assertIsNotNone(o, 'errrrr')
        self.assertEquals(o.registrant, 'TESLA, INC.')

        o2 = s.NumericData('0001564590-17-003118', 'OperatingExpenses', 'us-gaap/2016',
                       None, '20160930', 3,
                       'USD', 272512)
        self.assertIsNotNone(o2, 'errrrr')
        self.assertEquals(o2.tag, 'OperatingExpenses')
        self.assertEquals(o2.value, 272512)

        o3 = s.PresentationOfStatements('0001564590-17-003118', 2, 20,
                       'BS', 0, 'AccountsPayableCurrent',
                                      'us-gaap/2016', 'Accounts payable')
        self.assertIsNotNone(o3, 'errrrr')
        self.assertEquals(o3.tag, 'AccountsPayableCurrent')

    def test_getFiles(self):
        year=2017
        quarter='q1'
        self.assertEquals(
            "http://www.sec.gov/data/financial-statements/{year}/{quarter}.zip".format(year=year, quarter=quarter)
        , "http://www.sec.gov/data/financial-statements/2017/q1.zip")

        self.assertEquals('0000002178-17-000019', s.getRegistrantIds(2017, 1, 'ADAMS RESOURCES & ENERGY, INC.'))

if __name__ == '__main__':
    unittest.main()

    '''
    def getRegistrantIds(year, quarter, registrant):
        result = None
        filename = "{year}q{quarter}.zip".format(year=year, quarter=quarter)
        if os.path.exists(RESOURCES_FOLDER):
            folder = RESOURCES_FOLDER + "/" + "{year}q{quarter}".format(year=year, quarter=quarter)
        else:
            folder = getFile(year, quarter)
            if None == folder:
                print('something wrong')

        df = pd.DataFrame.from_csv(folder + '/' + SUBMISSIONS_FILE, sep='\t', header=0, index_col=None)
        result = df[df['name'] == registrant ]['adsh'][0]
        return result


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

    '''