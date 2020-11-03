import sys
from unittest.mock import patch, Mock
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


def test_strategy_mock_library(initialize):
    solver = Mock()
    solver.solve.solve.return_value = 6
    solver.solve(3) == 6
    solver.solve.assert_called_once()


def test_strategy_unittest_library(initialize):
    strategy = Triple()
    triple.compute = Mock()
    Strategy.factory.side_effect = triple
    triple.compute.side_effect = 5
    solver = Solver(triple)
    assert solver.solve(3) == 6


