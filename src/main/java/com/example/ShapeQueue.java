package com.example;

import java.util.Random;

public class ShapeQueue {

    private final Shape[] shapes = {
        new IShape(), new JShape(), new LShape(), new OShape(), new SShape(), new TShape(), new ZShape()
    };

    private final Random random = new Random();
    
    private Shape nextShape;

    ShapeQueue(){
        nextShape = randomShape();
    }

    /**
     * This is a helper method for returning a random shape from the blocks array.
     * @return
     */
    private Shape randomShape() {
        return shapes[random.nextInt(shapes.length)]; //nextInt returns an int 0-6
    }

    /** 
     *  This is the method the user calls upon, it automatically re-selects a random object after returning the current shape
     *  
     */ 
    public Shape getRandomShape(){
        Shape shape = this.nextShape;
        do {
            this.nextShape = randomShape();
            
        } while (shape.getId() == nextShape.getId());
        return shape;
    }
    
}
