
By wprowadzić aktualne współrzędne oraz identyfikatory beaconów wymagana jest zmiana w metodzie loadConfigurationHome(LinkedList<Beacon> target) lub  load316(LinkedList<Beacon> target) znajdującej się w EvaluationService.java.
Dodanie lub zmiana polożenia wymaga wprowadzenia następującej linijki:
target.add(new Beacon(nazwa, major, minor, x, y));
gdzie nazwa - string oznaczający nazwe beacona
major - major beacona (int)
minor - minor beacona (int)
x - położenie beacona na osi X (double)
y - położenie beacona na osi Y (double)



