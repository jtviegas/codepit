import json
import logging
import os
import time
import random

from opencensus.ext.azure import metrics_exporter
from opencensus.ext.azure.log_exporter import AzureLogHandler
from opencensus.stats import aggregation as aggregation_module
from opencensus.stats import measure as measure_module
from opencensus.stats import stats as stats_module
from opencensus.stats import view as view_module
from opencensus.tags import tag_map as tag_map_module

SECRETS_FILE = f"{os.path.dirname(os.path.realpath(__file__))}/.secrets"
secrets = None


def get_secrets():
    result = None
    with open(SECRETS_FILE) as json_file:
        # json content is in itself a dict
        result = json.load(json_file)
    return result


class Metric:

    def __init__(self, key):
        self.__measure = measure_module.MeasureInt("duration", "duration in minutes", "m")
        self.__view = view_module.View("duration_view",
                                     "duration in minutes",
                                     ["app", "stage"],
                                     self.__measure,
                                     aggregation_module.LastValueAggregation())
        self.__exporter = metrics_exporter.new_metrics_exporter(enable_standard_metrics=False, export_interval=5.0,
                                                         connection_string=f"InstrumentationKey={key}")
        stats_module.stats.view_manager.register_exporter(self.__exporter)
        stats_module.stats.view_manager.register_view(self.__view)
        self.__tmap = tag_map_module.TagMap()
        self.__tmap.insert("app", "test")
        self.__tmap.insert("stage", "dev")

        self.__mmap = stats_module.stats.stats_recorder.new_measurement_map()

    def log(self, val):
        self.__mmap.measure_int_put(self.__measure, val)
        self.__mmap.measure_put_attachment("application", "testing")
        self.__mmap.record(self.__tmap)


class DagLogHandler(AzureLogHandler):

    def __init__(
        self,
        app: str = None,
        params: dict = None,
        **options
    ):
        self.app = app
        self.params = params
        super().__init__(**options)

    def log_record_to_envelope(self, record):
        if not hasattr(record, "custom_dimensions") or record.custom_dimensions is None:
            record.custom_dimensions = {}
        record.custom_dimensions.update(  { "test": json.dumps( { "app": self.app, } ) } )
        return super().log_record_to_envelope(record=record)


def init_logging(key):

    logging.basicConfig()

    base_logger = logging.getLogger()
    base_logger.setLevel("DEBUG")
    logging.getLogger("py4j").setLevel("ERROR")

    handler = DagLogHandler( app="test", connection_string=f"InstrumentationKey={key}" )
    base_logger.addHandler(handler)
    base_logger.info("Job init_logging")




def main():

    metric = Metric(get_secrets()['azure_app_insights_instrumentation_key'])
    # init_logging(get_secrets()['azure_app_insights_instrumentation_key'])

    for i in range(0, 100):
        val = random.randint(10, 60)
        metric.log(val)
        #logging.info(val)
        print(f"{i}: sent {val}")
        time.sleep(10)

    # Default export interval is every 15.0s
    # Your application should run for at least this amount
    # of time so the exporter will meet this interval
    # Sleep can fulfill this
    time.sleep(60)

    print("Done recording metrics")

if __name__ == "__main__":
    main()

