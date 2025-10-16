package TwentyFortyEight;

public class Cell {

    private int x;
    private int y;
    private int value;
    private boolean merge;
    

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        value = 0;
        merge = false;
    }

    //add random value
    public void place() {
        if (this.value == 0) {
            this.value = (App.random.nextInt(2)+1)*2;
            //this.value = 2;
        }
    }

    public void setValue(int v) {
        value = v;
    }

    public void combine() {
        value = value*2;
        merge = true;
    }

    public int getValue() {
        return value;
    }

    public boolean getMerge() {
        return merge;
    }

    public void setMerge(boolean m) {
        merge = m;
    }

    /**
     * This draws the cell
     */
    public void draw(App app) {
        app.stroke(156, 139, 124);
            if (this.value == 0 && app.mouseX > x*App.CELLSIZE && app.mouseX < (x+1)*App.CELLSIZE 
                && app.mouseY > y*App.CELLSIZE && app.mouseY < (y+1)*App.CELLSIZE) {
                app.fill(232, 207, 184);
            // } else if (this.value == 2) {
            //     // using images is optional. You can choose to draw rectangles of different colours
            //     // maybe make a hashmap to store colour values of each number type
            //     app.image(app.eight, x*App.CELLSIZE, y*App.CELLSIZE);
            } else if (this.value == 2) { 
                app.fill(238, 228, 218);
            } else if (this.value == 4) { 
                app.fill(238, 228, 200);
            } else if (this.value == 8) { 
                app.fill(242, 177, 121);
            } else if (this.value == 16) { 
                app.fill(245, 149, 99);
            } else if (this.value == 32) { 
                app.fill(246, 124, 95);
            } else if (this.value == 64) { 
                app.fill(246, 94, 59);
            } else if (this.value == 128) { 
                app.fill(237, 207, 114);
            } else if (this.value == 256) { 
                app.fill(237, 204, 97);
            } else if (this.value == 512) { 
                app.fill(237, 200, 80);
            } else if (this.value == 1024) { 
                app.fill(238, 197, 63);
            } else if (this.value > 2000) { 
                app.fill(238, 194, 46);
            } else {
                app.fill(189, 172, 151);
            }
       // if (this.value != 2) {
            app.rect(x * App.CELLSIZE, y * App.CELLSIZE, App.CELLSIZE, App.CELLSIZE);
            if (this.value > 10000) {
                app.textSize(25);
                app.fill(0, 0, 0);
                app.text(String.valueOf(this.value), (x+ 0.1f) * App.CELLSIZE, (y + 0.6f) * App.CELLSIZE);
            } else if (this.value > 1000) {
                app.textSize(30);
                app.fill(0, 0, 0);
                app.text(String.valueOf(this.value), (x+ 0.1f) * App.CELLSIZE, (y + 0.6f) * App.CELLSIZE);
            } else if (this.value > 100) {
                app.fill(0, 0, 0);
                app.text(String.valueOf(this.value), (x + 0.1f) * App.CELLSIZE, (y + 0.6f) * App.CELLSIZE);
            } else if (this.value > 10) {
                app.fill(0, 0, 0);
                app.text(String.valueOf(this.value), (x + 0.3f) * App.CELLSIZE, (y + 0.6f) * App.CELLSIZE);
            } else if (this.value > 0) {
                app.fill(0, 0, 0);
                app.text(String.valueOf(this.value), (x + 0.4f) * App.CELLSIZE, (y + 0.6f) * App.CELLSIZE);
            }
        //}
    }

}
