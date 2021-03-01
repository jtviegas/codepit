from tests.factories.Sink import Sink


def test_one():
    sink = Sink.factory('DbSink')
    sink.put('s')
    sink = Sink.factory('FileSink')
    sink.put('s')