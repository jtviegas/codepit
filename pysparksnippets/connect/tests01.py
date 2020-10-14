from pyspark.sql import SparkSession


def get_dbutils(spark):
  if spark.conf.get("spark.databricks.service.client.enabled") == "true":
    from pyspark.dbutils import DBUtils
    return DBUtils(spark.sparkContext)
  else:
    import IPython
    return IPython.get_ipython().user_ns["dbutils"]


spark = SparkSession.builder.getOrCreate()
# spark.read.table("adl_prod.consumer").limit(10).show()

dbutils = get_dbutils(spark)
print(dbutils.fs.ls("dbfs:/"))
print(dbutils.secrets.listScopes())