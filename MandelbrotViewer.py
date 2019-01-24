ITER = 1000
FRAME = 50
ZOOM = 2

c = []
for x in range(FRAME):
    c.append([])
    for y in range(FRAME):
        c[x].append([])
        c[x][y].append(0)
        c[x][y].append(0)
p = []
for x in range(FRAME):
    p.append([])
    for y in range(FRAME):
        p[x].append(0)

def is_mandelbrot(r,i):
    """tests a complex number for membership in the
    mandelbrot set and returns the number of iterations
    transpired""" 
    z, z0, i0, ite = 0.0, 0.0, 0.0, 0
    for x in range(ITER):
        z0 = z
        z = pow(z,2) + r
        z += (i0 * i0 * -1)
        i0 *= (z0 * 2)
        i0 += i
        if z > 2 or z < -2:
            return x
    return ITER

def make_complex(row,col,zoom):
    """creates a 2d list of complex numbers fit to the number
    of "pixels" and the current penatration, and passes on a
    2d list of the number of iterations each complex number
    passes through"""
    INC = (4/float(FRAME)) / zoom

    imag = c[row][col][0]
    real = c[row][col][1]
	
    x, i, = 0, imag + (2.0 / zoom)
    while x < FRAME:
        r = real - (2.0 / zoom)
        y = 0
        while y < FRAME:
            c[x][y][0] = i
            c[x][y][1] = r

            p[x][y] = is_mandelbrot(r,i)

            y += 1
            r += INC
        x += 1
        i -= INC
    display_mandelbrot(p)

def display_mandelbrot(p):
    """displays the output of make_complex"""
    for x in range(FRAME):
        for y in range(FRAME):
            if y == 0:
                if x < 9:
                    print(" ", end='')    
                print(x+1, end='')
                print(" ", end='')    
            if p[x][y] == ITER:
                print("xxx", end='')
            elif p[x][y] > ITER * 0.1:
                print("^ ^", end='')
            elif p[x][y] > ITER * 0.25:
                print("<< ", end='')
            elif p[x][y] > ITER * 0.35:
                print(" + ", end='')
            elif p[x][y] > ITER * 0.5:
                print(" * ", end='')
            elif p[x][y] > ITER * 0.6:
                print(" >>", end='')
            elif p[x][y] > ITER * 0.75:
                print(" # ", end='')
            else:
                print(" . ", end='')
            if y+1 == FRAME:
            	print("")
        if x+1 == FRAME:
            print("   ", end='')
            for z in range(FRAME):
                print(z+1, end='')
                print(" ", end='')
                if z < 9:
                    print(" ", end='')

row, col,  zoom = 0, 0, 1.0

while(True):
    make_complex(row,col,zoom)

    cc_0 = c[int(FRAME/2)][int(FRAME/2)][1]
    cc_1 = c[int(FRAME/2)][int(FRAME/2)][0]
    if cc_0 < 0.01 and cc_0 > -0.01:
        cc_0 = 0
    if cc_1 < 0.01 and cc_1 > -0.01:
        cc_1 = 0

    ui = input("\n\n"+"MANDELBROT | current zoom: "+str(zoom)+
               "x | current center: "+str(cc_0)+" + "+
               str(cc_1)+"i\n\n"+
              "TO ZOOM FURTHER, TYPE NUMBER OF ROW\n\n"+
              "(type back to reset)\n"+
              "(type end to quit)\n\n")
    if ui == "back":
        row = int(FRAME / 2)
        col = int(FRAME / 2)
        zoom = 1.0
        c[row][col][0] = 0.0
        c[row][col][1] = 0.0
        continue
    if ui == "end":
        break

    row = int(ui) - 1
    ui = input("\n"+"TYPE NUMBER OF COLUMN\n\n")
    col = int(ui) - 1
    zoom *= ZOOM
