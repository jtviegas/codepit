import importlib
import logging
import os
import pkgutil
from abc import ABC, abstractmethod


class Strategy(ABC):

    def __init__(self) -> None:
        self.logger = logging.getLogger(
            f'{__name__}.{self.__class__.__name__}',
        )

    @abstractmethod
    def compute(self, value):
        pass

    @staticmethod
    def factory(config):

        pkg_dir = os.path.dirname(__file__)
        for (module_loader, name, ispkg) in pkgutil.iter_modules([pkg_dir]):
            importlib.import_module('.' + name, __package__)

        clazz = [cls for cls in Strategy.__subclasses__() if cls.__name__ == config['strategy']['impl']][0]
        return clazz()



