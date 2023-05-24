
# TEACHER PART

class Graph:
    # maiilon Prec = principal
    class MaillPrint:
        def __init__(self, origin, type):
            # origin :> name of primary sommet (teach madde it this way)
            self._ori = origin
            # principal list or secondary (neighbors)
            self._type = type
            # listed is a flage used by teacher
            self._listed = False
            self._list = None
            self._next = None

    class MailloSec:
        def __init__(self, destination, fiab, dist, dur):
            self._dest = destination
            self._fiab = fiab
            self._dist = dist
            self._dur = dur
            self._next = None

    def __init__(self, num):
        self._v = num
        self._first = None

    def addMain(self, ori, t):
        new = Graph.MaillPrint(ori, t)
        new._next = self._first
        self._first = new

    def addEdge(self, o, d, fiab, dist, dur):
        # connect from distionation
        new = Graph.MailloSec(d, fiab, dist, dur)
        temp = self._first
        while temp._ori != o:
            temp = temp._next
        new._next = temp._list
        temp._list = new

        # here we are connecting from the opposite side : Origin
        temp = self._first
        while temp._ori != d:
            temp = temp._next
        new._next = temp._list
        temp._list = new

    def printBloc(self):
        temp = self._first
        while temp != None:
            if temp._type == 'Bloc':
                print(temp._ori)
            temp = temp._next

    def printMat(self):
        temp = self._first
        while temp != None:
            if temp._type == 'Materinite':
                print(temp._ori)
            temp = temp._next

    def printWays(self):
        temp = self._first
        while temp != None:
            print("Trajet de " + temp._ori + ' to :')
            temp2 = temp._list
            while temp2 != None:
                print("  " + temp2._dest + " + fiabilite " + str(temp2._fiab) +
                      ", distance  " + str(temp2._dist) + ", duree " + str(temp2._dur))
                temp2 = temp2._next
            temp = temp._next

    # teach say it is not important
    def printWays2(self):
        temp = self._first
        while temp != None:
            print("Trajet de " + temp._ori + ' to :')
            temp._listed = True
            temp2 = temp._list
            while temp2 != None:
                t = self._first
                while t._ori != temp2._dest:
                    t = t._next
                if not t._listed:
                    print("  " + temp2._dest + " + fiabilite " + str(temp2._fiab) +
                          ", distance  " + str(temp2._dist) + ", duree " + str(temp2._dur))
                temp2 = temp2._next
            temp = temp._next
            self._clearListed()

    def oneDistNeighbors(self, disp):
        temp = self._first
        while temp._ori != disp:
            temp = temp._next
        temp2 = temp._list
        print("les 1-dist voisins de " + disp + " dont :")
        while temp2 != None:
            print(temp2._dist, end=", ")
            temp2 = temp2._next

    def printS(self):
        temp = self._first
        while temp != None:
            print(temp._ori + " :")
            temp2 = temp._list
            while temp2 != None:
                print(temp2._dest + ". ", end="")
                temp2 = temp2._next
            print('\n')

            temp = temp._next

        # c = temp._list
        # while c != None:
        # c = c._next


v = Graph(3)
v.addMain('a', 'nutri')
v.addMain('b', 'med')
v.addMain('c', 'Bloc')
v.addEdge('a', 'b', 0.5, 10, 2)
v.addEdge('b', 'c', 0.9, 20, 4)
v.addEdge('c', 'a', 0.2, 5, 1)
# v.printS()
# v.printBloc()
v.printWays()




#MY PART

class LSommet:
    class MailloSommet:
        def __init__(self, val):
            self._valeu = val
            self._suiv = None
            self._linkgo = None
            self._linkback = None

    class MailloLink:
        def __init__(self, destination, distance, longeur):
            self._destination = destination
            self._distance = distance
            self._long = longeur
            self._suiv = None

    def __init__(self):
        self._prem = None
        self._nbElement = 0

    def add(self, v):
        nval = LSommet.MailloSommet(v)
