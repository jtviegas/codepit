from typing import Optional


def justafunc(justavar: Optional[str]=None):
    _msg = "hey buddy"
    if justavar is not None:
        _msg = f"hey {justavar}"
    print(_msg)


def test_strategy():
    justafunc()