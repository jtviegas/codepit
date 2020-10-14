import sys
from unittest.mock import patch
import pytest
from computation.solver import Solver
from computation.strategy.Triple import Triple
from config import Injector


@pytest.fixture
def initialize():
    injector = Injector()
    injector.configure_logging()
    injector.wire(modules=[sys.modules[__name__]])


def test_strategy(initialize):
    solver = Solver()
    assert solver.solve(3) == 9


def test_strategy_mock_solver(initialize):
    with patch.object(Solver, 'solve', autospec=True) as mock_solver:
        mock_solver.return_value = 6
        solver = Solver()
        assert solver.solve(3) == 6


def test_strategy_mock_triple(initialize):
    with patch.object(Triple, 'compute', autospec=True) as mock_triple:
        mock_triple.return_value = 6
        solver = Solver()
        assert solver.solve(3) == 6

