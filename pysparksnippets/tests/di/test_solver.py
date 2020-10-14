import sys
import pytest

from computation.solver import Solver
from config import Injector


@pytest.fixture
def initialize():
    injector = Injector()
    injector.configure_logging()
    injector.wire(modules=[sys.modules[__name__]])


@pytest.mark.parametrize("input1, output", [(3,9),(7,21)])
def test_double(initialize, input1, output):
    solver = Solver()
    assert output == solver.solve(input1)

