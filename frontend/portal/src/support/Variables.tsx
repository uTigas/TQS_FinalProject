import { ReactNode, SetStateAction, createContext, useContext, useState } from "react";

export interface HeaderProps {
    name: string;
}

export interface StationData {
    name: string;
    numberOfLines: number;
  }

export interface Connection {
    name: string;
}

export interface User {
    username: string;
    name: string;
    role: string;
}

export enum TrainType {
    URBAN, REGIONAL, INTER, ALPHA, SPECIAL,
}


interface SharedVariables {
    loggedUser : User | null;
    setLoggedUser : React.Dispatch<React.SetStateAction<User | null>>;
    selectedContainer: number;
    setSelectedContainer: React.Dispatch<React.SetStateAction<number>>;
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
    results: any[];
    setResults: React.Dispatch<React.SetStateAction<any[]>>;

  }
  
  export const SharedVariablesContext = createContext<SharedVariables>({
      selectedContainer: 1,
      setSelectedContainer: () => { },
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
      loggedUser: null,
      setLoggedUser:() => { }
  });
  
  export const useSharedVariables = () => useContext(SharedVariablesContext);
  
  export const SharedVariablesProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [loggedUser, setLoggedUser] = useState<User | null>(null);
    const [selectedContainer, setSelectedContainer] = useState<number>(1);
    const [connections, setConnections] = useState<Connection[]>([]);
    const [possible, setPossible] = useState<Connection[]>([]);
    const [selectedOrigin, setSelectedOrigin] = useState<string>("");
    const [selectedDestination, setSelectedDestination] = useState<string>("");
    const [selectedDate, setSelectedDate] = useState<string>("");
    const [selectedReturnDate, setSelectedReturnDate] = useState<string>("");
    const [findReturn, setFindReturn] = useState<boolean>(false);
    const [results, setResults] = useState<any[]>([]);
  
    return (
      <SharedVariablesContext.Provider
        value={{
          loggedUser,
          setLoggedUser,
          selectedContainer,
          setSelectedContainer,
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
        }}
      >
        {children}
      </SharedVariablesContext.Provider>
    );
  };
  