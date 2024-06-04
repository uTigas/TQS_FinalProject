import { ReactNode, SetStateAction, createContext, useContext, useState } from "react";

export interface HeaderProps {
    name: string;
}

export interface StationData {
    name: string;
    numberOfLines: number;
  }

export interface Connection {
  id: string
  origin: StationData
  destination: StationData
  name: string
  arrivalTime: string
  departureTime: string
  lineNumber: number
  price: number
  train: number
}

export interface User {
    username: string;
    name: string;
    role: string;
}

export interface Route {
  arrival: string;
  departure: string;
  price: number;
  connections: Connection[]
}

export interface Ticket {
  id: string;
  totalPrice: number;
  timestamp: string;
  route: Connection[];
}

export enum TrainType {
    URBAN, REGIONAL, INTER, ALPHA, SPECIAL,
}


interface SharedVariables {
    loggedUser : User | null;
    setLoggedUser : React.Dispatch<React.SetStateAction<User | null>>;
    selectedContainer: number;
    setSelectedContainer: React.Dispatch<React.SetStateAction<number>>;
    stations : StationData[];
    setStations: React.Dispatch<React.SetStateAction<StationData[]>>;
    connections: any[];
    setConnections: React.Dispatch<React.SetStateAction<any[]>>;
    possible: any[];
    setPossible: React.Dispatch<React.SetStateAction<any[]>>;
    selectedOrigin: string;
    setSelectedOrigin: React.Dispatch<React.SetStateAction<string>>;
    selectedDestination: string;
    setSelectedDestination: React.Dispatch<React.SetStateAction<string>>;
    selectedDate: string;
    setSelectedDate: React.Dispatch<React.SetStateAction<string>>;
    selectedReturnDate: string;
    setSelectedReturnDate: React.Dispatch<React.SetStateAction<string>>;
    findReturn: boolean;
    setFindReturn: React.Dispatch<React.SetStateAction<boolean>>;
    goRoute: Route | null;
    setGoRoute: React.Dispatch<React.SetStateAction<Route| null>>;
    returnRoute: Route | null;
    setReturnRoute: React.Dispatch<React.SetStateAction<Route| null>>;
    results: any[];
    setResults: React.Dispatch<React.SetStateAction<any[]>>;

  }
  
  export const SharedVariablesContext = createContext<SharedVariables>({
      selectedContainer: 1,
      setSelectedContainer: () => { },
      stations: [],
      setStations: () => { },
      connections: [],
      setConnections: () => { },
      possible: [],
      setPossible: () => { },
      selectedOrigin: "",
      setSelectedOrigin: () => { },
      selectedDestination: "",
      setSelectedDestination: () => { },
      selectedDate: "",
      setSelectedDate: () => { },
      selectedReturnDate: "",
      setSelectedReturnDate: () => { },
      findReturn: false,
      setFindReturn: () => { },
      results: [],
      setResults: () => { },
      goRoute: null,
      setGoRoute: () => { },
      returnRoute: null,
      setReturnRoute: () => { },
      loggedUser: null,
      setLoggedUser:() => { }
  });
  
  export const useSharedVariables = () => useContext(SharedVariablesContext);
  
  export const SharedVariablesProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [loggedUser, setLoggedUser] = useState<User | null>(null);
    const [selectedContainer, setSelectedContainer] = useState<number>(1);
    const [connections, setConnections] = useState<Connection[]>([]);
    const [stations, setStations] = useState<StationData[]>([]);
    const [possible, setPossible] = useState<Connection[]>([]);
    const [selectedOrigin, setSelectedOrigin] = useState<string>("");
    const [selectedDestination, setSelectedDestination] = useState<string>("");
    const [selectedDate, setSelectedDate] = useState<string>("");
    const [selectedReturnDate, setSelectedReturnDate] = useState<string>("");
    const [findReturn, setFindReturn] = useState<boolean>(false);
    const [results, setResults] = useState<any[]>([]);
    const [goRoute, setGoRoute] = useState<Route | null>(null);
    const [returnRoute, setReturnRoute] = useState<Route | null>(null);

    return (
      <SharedVariablesContext.Provider
        value={{
          loggedUser,
          setLoggedUser,
          selectedContainer,
          setSelectedContainer,
          stations,
          setStations,
          connections,
          setConnections,
          possible,
          setPossible,
          selectedOrigin,
          setSelectedOrigin,
          selectedDestination,
          setSelectedDestination,
          selectedDate,
          setSelectedDate,
          selectedReturnDate,
          setSelectedReturnDate,
          findReturn,
          setFindReturn,
          results,
          setResults,
          goRoute,
          setGoRoute,
          returnRoute,
          setReturnRoute,
        }}
      >
        {children}
      </SharedVariablesContext.Provider>
    );
  };
  