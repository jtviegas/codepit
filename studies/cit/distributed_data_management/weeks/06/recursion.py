
#--------------------------------------------------------
#           PYTHON PROGRAM
# Here is where we are going to define our set of...
# - Imports
# - Global Variables
# - Functions
# ...to achieve the functionality required.
# When executing > python 'this_file'.py in a terminal,
# the Python interpreter will load our program,
# but it will execute nothing yet.
#--------------------------------------------------------

#--------------------------------------------------
# recursive_add
#--------------------------------------------------
def recursive_add(l):
	#1. We create the output variable
	res = 0
		
	#2. We compute the length, so as to discriminate between recursive and base cases
	size = len(l)
		
	#3. Base case: Add all elements of an empty list
	if (size == 0):
		#3.1. Easy problem: Add all elements of an empty list returns 0
		res = 0
	#4. Recursive case: Add all elements of a non-empty list
	else:
		#4.1. We take care of the last element of the list
		value = l[size-1]

		#4.2. We reduce the problem P to a simpler problem P' by removing one element from the list
		del l[size-1]
			
		#4.3. We ask the simpler problem P' to be solved.
		recursive_result = recursive_add(l)
			
		#4.4. Once solved, we bring the problem P' to its original size P by adding 'value' back to the list
		l.append(value)
			
		#4.5. We compute the solution of the problem P by adding 'value' to the solution of P'
		res = recursive_result + value
		
	#5. We return the output variable
	return res

#--------------------------------------------------
# recursive_fibonacci
#--------------------------------------------------
def recursive_fib(n):
	#1. We create the output variable 
	res = 0

	#2. Base case: Getting the first or second element of the sequence
	if ((n == 0) or (n == 1)):
		#2.1. In this case the result is straight away 0 or 1. 
		res = n
	#3. Recursive case: Getting the i-est element of the sequence (with i >= 2) 
	else:
		#3.1. In this case the i-est element is computed as the sum of the previous two elements of the sequence. 
		recursive_res1 = recursive_fib(n-1)
		recursive_res2 = recursive_fib(n-2)
		res = recursive_res1 + recursive_res2
		
	#4. We return the output variable
	return res

#--------------------------------------------------
# extra_hanoi
#--------------------------------------------------
def extra_hanoi(n, source, middle, destination, movements):
	# 1. Base case:
	if n == 1:
		# 1.1. Get the top disk from the source column
		s_disks = source[1]
		size = len(s_disks)
		top_disk = s_disks[size-1]

		# 1.2. Remove the top disk from the source column
		del s_disks[size-1]

		# 1.3. Add the disk to the destination
		d_disks = destination[1]
		d_disks.append(top_disk)

		# 1.4. Create the new movement
		mov = (top_disk, source[0], destination[0])

		# 1.5. Add the new movement to the list of movements
		movements.append(mov)
	else:
	# 2. Recursive case:
		# 2.1. Move n-1 disks from source to middle (using destination as intermediate stack)
		extra_hanoi(n-1, source, destination, middle, movements)

		# 2.2. Move 1 disks from source to destination (using middle as intermediate stack)
		extra_hanoi(1, source, middle, destination, movements)

		# 2.3. Move n-1 disks from middle to destination (using source as intermediate stack)
		extra_hanoi(n-1, middle, source, destination, movements)

# --------------------------------------------------
# hanoi
# --------------------------------------------------
def hanoi(n):
	# 1. Get the initial status of the game

	# 1.1. Initialise the source
	s_disks = []
	for i in range(n):
		s_disks.append(n-i)
	source = ("A", s_disks)

	# 1.2. Initialise the middle
	middle = ("B", [])

	# 1.3. Initialise the destination
	destination = ("C", [])

	# 1.4. Initialise the movements
	movements = []

	# 2. Solve the game
	extra_hanoi(n, source, middle, destination, movements)

	# 3. Show the solution
	for i in range(0, len(movements)):
		(disk, s_name, d_name) = movements[i]
		print("Move ", i+1, "Disk = ", disk, " from ", s_name, " to ", d_name)

#--------------------------------------------------
# my_main
#--------------------------------------------------
def my_main():
	# 1. We test the result for recursive_add
	l = [3,2,9]
	res = recursive_add(l)
	print(res)

	# 2. We test the result for recursive_fib
	res = recursive_fib(4)
	print(res)

	# 3. We test the result for hanoi
	hanoi(4)

#---------------------------------------------------------------
#           PYTHON EXECUTION
# This is the main entry point to the execution of our program.
# It provides a call to the 'main function' defined in our
# Python program, making the Python interpreter to trigger
# its execution.
#---------------------------------------------------------------

if __name__ == "__main__":
	my_main()
