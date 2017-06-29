import random
import sys
import os

print("Hello World")


# Comment

'''
Multiline
'''

variable = "Hasan"
print(variable)

# Numbers

# Strings


# + - * /
# %  - modulus
# ** - exponent
# // - Floor division

string = "I am not "

multi_line_quote = '''just 
like everyone else'''

string + multi_line_quote # string concatenation

print("%s %s %s" % ('I like the quote', string, multi_line_quote))

print('\n' * 5)
print("I don't like ", end="")
print("newlines")

# Lists
grocery_list = ['Juice', 'Tomatoes', 3, (2+1)]
print('First Item', grocery_list[0])

grocery_list[0] = 'Green Juice'
print(grocery_list[1:2]) # Exclude last index


other_events = ['Wash', 'Pickup',
                'Cash Check']

to_do_list = [other_events, grocery_list]

print(to_do_list)
print(to_do_list[0][2])

grocery_list.append('Onions') # Append
grocery_list.insert(1, 'Pickle') # Insert at second index
grocery_list.remove('Pickle') # Remove by element equality
other_events.sort()
grocery_list.reverse()

print(grocery_list)
del grocery_list[3] # Delete an element
print(to_do_list) # Changes effect to_do_list


to_do_list2 = other_events + grocery_list # Join two list

print(len(to_do_list))
print(max(other_events))


# Tuples
# Once created is we can't change it

pi_tuple = (3,1,4,1,5,9)
new_list = list(pi_tuple) # Convert tuple to list
new_tuple = tuple(new_list) # Convert list to tuple

print(new_list)
print(new_tuple)

len(pi_tuple)
min(pi_tuple)
max(pi_tuple)


# Dictionaries / Maps
# Unique key for each value
super_villians = {'Fiddler': 'Issac Bowin',
                  'Captain Cold': 'Leonard Snart',
                  'Weather Wizard': 'Mark Mardon',
                  'Mirror Master': 'Sam Scudder',
                  'Pied Piper': 'Thomas Peterson'
                  }
print(super_villians['Captain Cold'])

del super_villians['Fiddler']
print(super_villians)

super_villians['Pied Piper'] = 'Hartley Rathaway'

print(len(super_villians))
print(super_villians.get('Pied Piper'))
print(super_villians.keys())
print(super_villians.values())

# Conditions
# if else elif
# == != > >= <=
# and or

# White space is used to group blocks of code
age = 21

if age >= 21:
    print('You are old enough to drive a tractor trailer')
elif age >= 16:
    print('You are old enough to drive')
else:
    print('You are not old enough to drive')


if ((age >= 1) and (age <= 18)) : # Multiple conditions match
    print('You get a birthday')
elif (age == 21) or (age >=65): # Once a condition - no other condition
    print ('You get a birthday')
elif not(age == 30):
    print ("You don't get birthday")
else:
    print("You get a birthday part yeah")


# for x in range(0, 10): # Goes upto last but one number
#     print(x, ' ', end="")

print('\n')

for y in grocery_list:
    print(y)

for x in range(0, len(to_do_list)):
    for y in to_do_list[x]:
        print(y)

random_number = random.randrange(0, 100) # 0,..., 99
while(random_number  != 15):
    print(random_number)
    random_number = random.randrange(0, 100)

i = 0


while(i <= 20):
    if(i%2 == 0):
        print(i)
    elif (i==9):
        break # Jump out of loop al together
    else:
        i+=1
        continue # Skip rest in particular loop

    i += 1;


print('\n'* 5)

# Functions
def addNumber(fNum, lNum):
    sumNum = fNum + lNum # Not available out of function scope
    return sumNum

print(addNumber(1,2))

# Get user input

print('You name')
#name = sys.stdin.readline()
#print('Hello ', name)

long_string = "I'll catch you if you fail - The Floor"
print(long_string[0:4])
print(long_string[-5:])
print(long_string[:-5])

# %c character
# %s string
# %d signed integer
# %.5f atleast 4 decimal places
print("%c is my %s letter and my number %d number is %.5f",
      'X', 'favourite', 1, 0.14)

print(long_string.capitalize())

print(long_string.find("Floor")) # Find index of first match

print(long_string.isalnum())

print(len(long_string))

print(long_string.replace("Floor", "Ground"))

print(long_string.strip())

quote_list = long_string.split(" ")

print(quote_list)


# File Operations
test_file = open('test.txt', 'wb') # Write to file
    # "ab+" - read and append

print(test_file.mode)
print(test_file.name)
test_file.write(bytes("Write me to file\n", 'UTF-8'))
test_file.close()

test_file = open('test.txt', 'r+')
text_in_file = test_file.read()
print(text_in_file)

os.remove('test.txt')

# Objects/ Classes

class Animal:
    __name = "" # __ => Private
    __height = 0
    __weight = 0
    __sound = None # None indicates lack of value


    # Constructor __init__
    def __init__(self, name, height, weight, sound):
        self.__name = name
        self.__height = height
        self.__weight = weight
        self.__sound = sound


    # Encapsulation - setter and getter methods
    def set_name(self, name): # self - allows object to refer itself inside class
        self.__name = name

    def get_name(self, name):
        return self.__name

    def get_type(self):
        print("Animal")

    def get_sound(self):
        return self.__sound

    def toString(self):
        return("{} is {} cm tall".format(self.__name, self.__height))


cat = Animal('Whiskers', 33, 10, 'Meow')
print(cat.toString())


# Inheritence
class Dog(Animal):
    __owner = ""

    def __init__(self, name, height, weight, sound, owner):
        __owner = owner
        super(Dog, self).__init__(name, height, weight, sound)  # Calling a super class method
    # DOUBT: Why is super like this?

    def get_type(self): # Overwriting method
        print("Dog")


    # Method overloading
    def multiple_sounds(self, how_many=None):
        if (how_many is None):
            print(self.get_sound())
        else:
            print(self.get_sound() * how_many)

spot = Dog("Spot", 53, 27, "Ruff", "Hasan")
spot.get_type()



# Polymorphism
class AnimalTesting:
    def get_type(self, animal):
        animal.get_type()

test_animals = AnimalTesting()
test_animals.get_type(cat)
test_animals.get_type(spot)

spot.multiple_sounds(4)
spot.multiple_sounds()
