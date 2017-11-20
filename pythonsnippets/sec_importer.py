import urllib3 as u
import wget as w
import os
import sys
import zipfile
import datetime as dt
import pandas as pd

FILES = {
    "sub": 'sub.txt'
    #, "tag": 'tag.txt'
    #, "pre": 'pre.txt'
    , "num": 'num.txt'
}

def downloadFiles(year, quarter, folder):
    filename = "{year}q{quarter}.zip".format(year=year, quarter=quarter)
    url = "http://www.sec.gov/data/financial-statements/{filename}".format(filename=filename)
    file = os.getenv("TMP", "/tmp") + "/" +  filename
    #folder = "{rootpath}/sec_importer_{ts}".format(rootpath=os.getenv("TMP", "/tmp"), ts=dt.datetime.now().strftime("%s"))
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

def loadDataFromFiles(year, quarter, folder):
    #downloadFiles(year, quarter, folder)
    result = {
        'sub': None
        , 'pre': None
        , 'num': None
        , 'tag': None
    }
    for k, v in FILES.items():
        result[k] = pd.DataFrame.from_csv(folder + '/' + v, sep='\t', header=0, index_col=None)
    return result

def getTags():
    result = []
    result.append('Revenues')
    return result

def collectMetrics(data):
    entries=[]
    tags = getTags()
    for index, sub in data['sub'].iterrows():
        entry = {}
        id = sub['adsh']
        print('processing metrics for id: {id}, name:{name}'.format(id=id, name=sub['name']))

        numbers = data['num'][data['num']['adsh'] == id]
        for i, num in numbers.iterrows():
            if num['tag'] in tags:
                entry = {}
                entry['submission_id'] = id
                entry['registrant_id'] = sub['cik']
                entry['registrant'] = sub['name']
                entry['industry_class'] = sub['sic']
                entry['balancesheet_date'] = sub['period']
                entry['fiscal_period'] = sub['fp']
                entry['tag'] = num['tag']
                entry['enddate'] = num['ddate']
                entry['quarters'] = num['qtrs']
                entry['unit'] = num['uom']
                entry['value'] = num['value']
                entries.append(entry)

        if index == 6:
            break
    return pd.DataFrame(entries)

def applyRules(metrics):
    pass

def showOutcomes(data):
    pass


def main(args):
    if 4 != len(args):
        sys.exit("must provide 3 arguments: year, quarter and folder")

    year = args[1]
    quarter = args[2]
    folder = args[3]

    #get fillings file from sec
    data = loadDataFromFiles(year, quarter, folder)

    # collect wanted metrics
    metrics = collectMetrics(data)
    print(metrics)

    # apply rules
    outcomes = applyRules(metrics)

    # show outcomes
    showOutcomes(outcomes)

if __name__ == "__main__":
    main(sys.argv)


