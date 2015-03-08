# DrawLang
Generate java code by drawing circles and lines and stuff

## What is it?
A paint program that turns color-coded lines and symbols into Java code. 

For example, this: 
![alt text](https://github.com/OwenMcNaughton/DrawLang/blob/master/Hello%20World.PNG "Hello World")

Compiles to this:

    public class Hello {
    
        public static void main(String[] args) {
            int a = 72;
            int b = 101;
            int c = 108;
            int d = 111;
            int e = 32;
            System.out.print(Character.toChars(a));
            System.out.print(Character.toChars(b));
            System.out.print(Character.toChars(c));
            System.out.print(Character.toChars(c));
            System.out.print(Character.toChars(d));
            System.out.print(Character.toChars(e));
            int f = 87;
            int g = 111;
            int h = 114;
            int i = 108;
            int j = 100;
            System.out.print(Character.toChars(f));
            System.out.print(Character.toChars(g));
            System.out.print(Character.toChars(h));
            System.out.print(Character.toChars(i));
            System.out.print(Character.toChars(j));
            
        }
    }

Obviously we've a ways to go!

## Current Functionality
Declare a block of code with a black loop, this creates a BagComponent.

Use green to declare variables inside bags, click inside them to increment their value by 1, drag inside them to 
increment by 10. 

Use red lines to connect variables to functions, and to connect bags to other bags. The direction of the line matters, 
so start it at the variable and end it at where it needs to go. 

Magenta is reserved for key symbols. So far there's a squiggle for the start of the program, and a line for input/output.
End a red line at a purple line to make it an output. Soon we'll have vice versa for input.

## Planned Functionality
Arithmetic operations as lines (i.e, join two red lines together to add their connected variables... join them in
a different way to subtract them...)

Flow control as key symbols.

Reusable functions.

Sketch variables (Mark a variable as a "sketch" with blue, now the compiler parses that drawing as an image)

OOP maybe

