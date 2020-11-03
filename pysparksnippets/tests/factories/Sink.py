from abc import ABC, abstractmethod


def sink_impl(clazz):
    Sink.implementations[clazz.__name__] = clazz


class Sink(ABC):
    implementations = {}

    @abstractmethod
    def put(self, *args, **kwargs):
        pass

    def factory(name):
        return Sink.implementations[name]()


@sink_impl
class DbSink(Sink):
    def put(self, *args, **kwargs):
        print('@DbSink')


@sink_impl
class FileSink(Sink):
    def put(self, *args, **kwargs):
        print('@FileSink')
