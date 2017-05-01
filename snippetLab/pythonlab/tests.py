import unittest

print("______________________ unit_tests")

def formatName(fn, sn):
    return "{f} {s}".format(f=fn.title(), s=sn.title())

class FormatNameTestCase(unittest.TestCase):
    """tests for formatName"""
    def setUp(self):
        print('setting up')

    def test_does_it_work(self):
        formatted_name = formatName('jonathan', 'swift')
        self.assertEqual(formatted_name, 'Jonathan Swift')

if __name__ == '__main__':
    unittest.main()