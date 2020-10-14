from dependency_injector.wiring import Provide

from computation.strategy.strategy import Strategy
from config import Injector


class Solver:

    def __init__(self, strategy: Strategy = Provide[Injector.strategy]):
        self.strategy = strategy

    def solve(self, value):
        return self.strategy.compute(value)
