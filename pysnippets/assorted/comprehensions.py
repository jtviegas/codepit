
def doit():
    print('===================== comprehensions ===========================')
    squares = list(map(lambda x: x**2, range(10)))
    print(squares)
    squares2 = [x**2 for x in range(10)]
    print(squares2)
    # flatten a vec
    vec = [ [1,2,4], [3,5,6,], [7,8,9] ]
    flatten = [x for subvec in vec for x in subvec ]
    print(flatten)
    # set comprehensions
    sc = { x for x in 'abcrdsar' if x not in 'abc' }
    print(sc)