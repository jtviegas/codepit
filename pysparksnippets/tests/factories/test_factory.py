from tests.factories.Sink import Sink


def test_one():
    sink = Sink.factory('DbSink')
    sink.put('s')
