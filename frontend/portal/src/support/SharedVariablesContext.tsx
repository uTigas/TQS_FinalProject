// SharedVariablesContext.tsx

import React, { createContext, useState, useContext, ReactNode } from 'react';
import { ConnectionProp } from './Header';

interface SharedVariables {
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
  setSelectedContainer: () => {},
  connections: [],
  setConnections: () => {},
  possible: [],
  setPossible: () => {},
  selectedOrigin: "",
  setSelectedOrigin: () => {},
  selectedDestination: "",
  setSelectedDestination: () => {},
  selectedDate: "",
  setSelectedDate: () => {},
  selectedReturnDate: "",
  setSelectedReturnDate: () => {},
  findReturn: false,
  setFindReturn: () => {},
  results: [],
  setResults: () => {},
});

export const useSharedVariables = () => useContext(SharedVariablesContext);

export const SharedVariablesProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [selectedContainer, setSelectedContainer] = useState<number>(1);
  const [connections, setConnections] = useState<ConnectionProp[]>([]);
  const [possible, setPossible] = useState<ConnectionProp[]>([]);
  const [selectedOrigin, setSelectedOrigin] = useState<string>("");
  const [selectedDestination, setSelectedDestination] = useState<string>("");
  const [selectedDate, setSelectedDate] = useState<string>("");
  const [selectedReturnDate, setSelectedReturnDate] = useState<string>("");
  const [findReturn, setFindReturn] = useState<boolean>(false);
  const [results, setResults] = useState<any[]>([]);

  return (
    <SharedVariablesContext.Provider
      value={{
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
