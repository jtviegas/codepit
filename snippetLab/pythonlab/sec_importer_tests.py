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