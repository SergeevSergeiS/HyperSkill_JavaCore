package metro;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

public class Main {

    static HashMap<String, HashMap<String, Station>> metros = new HashMap<>();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("ERROR");
            return;
        }

        JsonObject input = Util.readJsonData(args[0]);
        if (input == null) {
            System.out.println("Incorrect file");
            return;
        }

        getStations(input);

        showMenu();

    }

    private static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        menuLoop:while (true) {
            String[] command = scanner.nextLine().split(" ");
            if (command.length == 0) {
                System.out.println("Invalid command");
                continue;
            }

            ArrayList<String> params = parseCommand(command);

            switch (command[0].toLowerCase()) {
                case "/route":
                    if (params.size() != 4) {
                        System.out.println("Invalid command");
                    } else {
                        HashMap<String, Station> firstMetro = metros.get(params.get(0));
                        HashMap<String, Station> secondMetro = metros.get(params.get(2));

                        if (firstMetro == null || secondMetro == null) {
                            System.out.println("Invalid command");
                        } else {
                            Station firstStation = firstMetro.get(params.get(1));
                            Station secondStation = secondMetro.get(params.get(3));

                            if (firstStation == null || secondStation == null) {
                                System.out.println("Invalid command");
                            } else {
                                DoubleLinkedList<String> route = findRoute(firstStation, secondStation);
                                if (route == null) {
                                    System.out.println("No route found");
                                } else {
                                    DoubleLinkedList.Node<String> current = route.getHead();
                                    while (current != null) {
                                        System.out.println(current.getValue());
                                        current = current.getNext();
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "/fastest-route":
                    if (params.size() != 4) {
                        System.out.println("Invalid command");
                    } else {
                        HashMap<String, Station> firstMetro = metros.get(params.get(0));
                        HashMap<String, Station> secondMetro = metros.get(params.get(2));

                        if (firstMetro == null || secondMetro == null) {
                            System.out.println("Invalid command");
                        } else {
                            Station firstStation = firstMetro.get(params.get(1));
                            Station secondStation = secondMetro.get(params.get(3));

                            if (firstStation == null || secondStation == null) {
                                System.out.println("Invalid command");
                            } else {
                                Pair<DoubleLinkedList<String>, Integer> route =
                                        findFastestRoute(firstStation, secondStation);
                                if (route == null) {
                                    System.out.println("No route found");
                                } else {
                                    DoubleLinkedList.Node<String> current = route.getFirst().getHead();
                                    while (current != null) {
                                        System.out.println(current.getValue());
                                        current = current.getNext();
                                    }
                                    if (route.getSecond() != 0) {
                                        System.out.println("Total: " + route.getSecond() + " minutes in the way");
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "/exit":
                    break menuLoop;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private static Pair<DoubleLinkedList<String>, Integer> findFastestRoute(Station firstStation, Station secondStation) {

        HashMap<Station, Pair<Integer, Station>> fastestRoute = new HashMap<>();

        for (HashMap<String, Station> metro : metros.values()) {
            for (Station station : metro.values()) {
                fastestRoute.put(station, new Pair<>(Integer.MAX_VALUE, null));
            }
        }

        if (fastestRoute.keySet().stream().allMatch(station -> station.getTime() == 0)) {
            return new Pair<>(findRoute(firstStation, secondStation), 0);
        }

        ArrayList<Station> notSearched = new ArrayList<>(fastestRoute.keySet());

        fastestRoute.put(firstStation, new Pair<>(0, null));
        notSearched.remove(firstStation);

        Station currentStation = firstStation;

        while (notSearched.contains(secondStation)) {
            int timeToCurrentStation = fastestRoute.get(currentStation).getFirst();

            for (Station nextStation : currentStation.getNextStations()) {
                if (notSearched.contains(nextStation) && timeToCurrentStation + currentStation.getTime() <
                        fastestRoute.get(nextStation).getFirst()) {
                    fastestRoute.put(nextStation,
                            new Pair<>(timeToCurrentStation + currentStation.getTime(), currentStation));
                }
            }

            for (Station prevStation : currentStation.getPrevStations()) {
                if (notSearched.contains(prevStation) && timeToCurrentStation + prevStation.getTime() <
                        fastestRoute.get(prevStation).getFirst()) {
                    fastestRoute.put(prevStation,
                            new Pair<>(timeToCurrentStation + prevStation.getTime(), currentStation));
                }
            }

            for (Transfer transfer : currentStation.getTransfers()) {
                Station transferStation = metros.get(transfer.getLine()).get(transfer.getStation());

                if (transferStation == null) {
                    return null;
                } else {
                    if (notSearched.contains(transferStation) &&
                            timeToCurrentStation + 5 < fastestRoute.get(transferStation).getFirst()) {
                        fastestRoute.put(transferStation, new Pair<>(timeToCurrentStation + 5, currentStation));
                    }
                }
            }

            Station minStation = metros.get(notSearched.get(0).getLine()).get(notSearched.get(0).getName());
            for (Station station : notSearched) {
                if (fastestRoute.get(station).getFirst() < fastestRoute.get(minStation).getFirst()) {
                    minStation = metros.get(station.getLine()).get(station.getName());
                }
            }
            currentStation = minStation;
            notSearched.remove(minStation);
        }

        if (fastestRoute.get(secondStation).getFirst() == Integer.MAX_VALUE) {
            return null;
        } else {

            DoubleLinkedList<String> route = new DoubleLinkedList<>();
            Pair<Integer, Station> step = fastestRoute.get(secondStation);

            route.addFirst(secondStation.getName());

            while (step.getSecond() != null) {
                route.addFirst(step.getSecond().getName());

                Pair<Integer, Station> lastStep = fastestRoute.get(step.getSecond());

                if (lastStep.getSecond() != null &&
                        !lastStep.getSecond().getLine().equals(step.getSecond().getLine())) {
                    route.addFirst(step.getSecond().getLine());
                }

                step = lastStep;
            }

            return new Pair<>(route, fastestRoute.get(secondStation).getFirst());
        }

    }

    private static DoubleLinkedList<String> findRoute(Station firstStation, Station secondStation) {
        LinkedList<Step> steps = new LinkedList<>();
        ArrayList<Station> searched = new ArrayList<>();

        steps.offer(new Step(firstStation, null));
        searched.add(firstStation);

        Step resultStep = null;

        while (steps.peek() != null) {
            Step currentStep = steps.poll();
            if (currentStep.getCurrentStation().equals(secondStation)) {
                resultStep = currentStep;
                break;
            }

            ArrayList<Station> nextStations = currentStep.getCurrentStation().getNextStations();
            ArrayList<Station> prevStations = currentStep.getCurrentStation().getPrevStations();

            for (Station nextStation : nextStations) {
                if (!searched.contains(nextStation)) {
                    steps.offer(new Step(nextStation, currentStep));
                    searched.add(nextStation);
                }
            }

            for (Station prevStation : prevStations) {
                if (!searched.contains(prevStation)) {
                    steps.offer(new Step(prevStation, currentStep));
                    searched.add(prevStation);
                }
            }

            for (Transfer transfer : currentStep.getCurrentStation().getTransfers()) {
                Station transferStation = metros.get(transfer.getLine()).get(transfer.getStation());

                if (transferStation == null) {
                    return null;
                } else {
                    if (!searched.contains(transferStation)) {
                        steps.offerFirst(new Step(transferStation, currentStep));
                        searched.add(transferStation);
                    }
                }
            }
        }

        if (resultStep == null) {
            return null;
        } else {
            DoubleLinkedList<String> route = new DoubleLinkedList<>();

            while (resultStep != null) {
                route.addFirst(resultStep.getCurrentStation().getName());
                Step lastStep = resultStep.getLastStep();
                if (lastStep != null && !resultStep.getCurrentStation().getLine()
                        .equals(lastStep.getCurrentStation().getLine())) {
                    route.addFirst(resultStep.getCurrentStation().getLine());
                }
                resultStep = lastStep;
            }

            return route;
        }
    }

    private static ArrayList<String> parseCommand(String[] command) {
        ArrayList<String> params = new ArrayList<>();
        if (command.length < 2) {
            return params;
        }

        int endOfLastParam = 1;
        while (endOfLastParam != command.length) {
            if (command[endOfLastParam].startsWith("\"")) {
                if (command[endOfLastParam].endsWith("\"")) {
                    params.add(command[endOfLastParam].replace("\"", ""));
                } else {
                    StringBuilder sb = new StringBuilder(command[endOfLastParam]).append(" ");
                    while (endOfLastParam < command.length - 1) {
                        endOfLastParam++;
                        sb.append(command[endOfLastParam]).append(" ");
                        if (command[endOfLastParam].endsWith("\"")) {
                            break;
                        }
                    }
                    if (!sb.toString().endsWith("\" ")) {
                        return params;
                    }
                    params.add(sb.toString().trim().replace("\"", ""));
                }
            } else {
                params.add(command[endOfLastParam].replace("\"", ""));
            }
            endOfLastParam++;
        }

        return params;
    }

    private static void getStations(JsonObject input) {
        for (String entry : input.keySet()) {
            JsonArray metroJa = input.get(entry).getAsJsonArray();
            HashMap<String, Station> metro = new HashMap<>();

            for (JsonElement stationJe : metroJa){

                JsonObject stationJo = stationJe.getAsJsonObject();
                String stationName = stationJo.get("name").getAsString();
                JsonElement timeJe = stationJo.get("time");
                int transferTime = 0;
                if (timeJe != null && timeJe.isJsonPrimitive()) {
                    transferTime = stationJo.get("time").getAsInt();
                }

                Station station = new Station(stationName, entry, transferTime);
                if (!metro.containsKey(stationName)) {
                    metro.put(stationName, station);
                } else {
                    station = metro.get(stationName);
                    station.setTime(transferTime);
                }

                JsonElement stationTransfers = stationJo.get("transfer");
                if (stationTransfers.isJsonArray()) {
                    for (JsonElement transferJe : stationTransfers.getAsJsonArray()) {
                        JsonObject transferJo = transferJe.getAsJsonObject();
                        Transfer transfer = new Transfer(transferJo.get("line").getAsString(),
                                transferJo.get("station").getAsString());
                        station.addTransfer(transfer);
                    }
                } else if (stationTransfers.isJsonObject()) {
                    JsonObject transferJo = stationTransfers.getAsJsonObject();
                    Transfer transfer = new Transfer(transferJo.get("line").getAsString(),
                            transferJo.get("station").getAsString());
                    station.addTransfer(transfer);
                }

                JsonElement prevStations = stationJo.get("prev");
                if (prevStations.isJsonArray()) {
                    for (JsonElement prevStationJe : prevStations.getAsJsonArray()) {
                        String prevStationName = prevStationJe.getAsString();
                        if (metro.containsKey(prevStationName)) {
                            station.addPrevStation(metro.get(prevStationName));
                        } else {
                            Station prevStation = new Station(prevStationName, station.getLine(), 0);
                            station.addPrevStation(prevStation);
                            metro.put(prevStationName, prevStation);
                        }
                    }
                }

                JsonElement nextStations = stationJo.get("next");
                if (nextStations.isJsonArray()) {
                    for (JsonElement nextStationJe : nextStations.getAsJsonArray()) {
                        String nextStationName = nextStationJe.getAsString();
                        if (metro.containsKey(nextStationName)) {
                            station.addNextStation(metro.get(nextStationName));
                        } else {
                            Station nextStation = new Station(nextStationName, station.getLine(), 0);
                            station.addNextStation(nextStation);
                            metro.put(nextStationName, nextStation);
                        }
                    }
                }

            }

            metros.put(entry, metro);
        }
    }

}
