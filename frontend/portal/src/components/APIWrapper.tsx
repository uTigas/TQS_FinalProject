import { Connection, Route } from "../support/Variables";

const APIWrapper = {
  backendURI: "", 
  privateAPI: "private/api/v1/",
  adminAPI: "admin/api/v1/",
  publicAPI: "public/api/v1/",
  authAPI: "auth/",

  fetchUserDetails: async () => {
    try {
      console.log("APIWrapper: Fetching User Details...")

      return await fetch(APIWrapper.backendURI + APIWrapper.authAPI + 'user', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching User details', error);
    }
  },

  fetchStationList: async () => {
    try {
      console.log("APIWrapper: Fetching Stations...")

      return await fetch(APIWrapper.backendURI + APIWrapper.publicAPI + 'stations', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching Stations', error);
    }
  },
  
  addStation: async (stationName: string, stationLines: number) => {
    try {
      const response = await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'stations', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ 'name': stationName, 'numberOfLines': stationLines })
      });
      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`${errorText}`);
      }
      return response.json();
    } catch (error) {
      console.error(error);
      throw error;
    }
  },

  editStation: async (oldStationName: string, newStationName: string, stationLines: number) => {
    try {
      return await fetch(`${APIWrapper.backendURI + APIWrapper.adminAPI}stations/${oldStationName}`, {
        method: 'PUT',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: newStationName, numberOfLines: stationLines })
      });
    } catch (error) {
      console.error('Error editing Station', error);
    }
  },

  fetchTrainList: async () => {
    try {
      console.log("APIWrapper: Fetching Trains...")
      return await fetch(APIWrapper.backendURI + APIWrapper.publicAPI + 'trains', { method: 'GET', credentials: 'include' });
      return await fetch(APIWrapper.backendURI + APIWrapper.publicAPI + 'trains', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching Trains', error);
    }
  },

  addTrain: async (type: string, number: number) => {
    try {
      console.log("APIWrapper: Adding Trains...")

      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'trains', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ 'type': type, 'number': number })
      });
    } catch (error) {
      console.error('Error adding Train', error);
    }
  },
  fetchConnectionList: async () => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.publicAPI + 'connections', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching Connections', error);
    }
  },

  addConnection: async (origin: string, destination: string, trainNumber: number, departureTime: string, arrivalTime: string, lineNumber: number, price: number) => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'connections', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ "origin": {"name": origin}, "destination": {"name": destination}, "train": trainNumber, "departureTime": departureTime, "arrivalTime": arrivalTime, "lineNumber": lineNumber, "price": price })
      });
    } catch (error) {
      console.error('Error adding Connection', error);
    }
  }

  searchRoutes: async (searchParams: URLSearchParams) => {
    try {
      console.log("APIWrapper: Searching Routes...")
      return await fetch(APIWrapper.backendURI + APIWrapper.publicAPI + `routes?${searchParams.toString()}`, 
      { method: 'GET', 
      credentials: 'include'
      }
      );
    } catch (error) {
      console.error('Error searching Routes', error);
    }
  },

 buyTicket: async (route: Route) => {
    try {
      console.log("APIWrapper: buying Ticket...")

      return await fetch(APIWrapper.backendURI + APIWrapper.privateAPI + 'tickets', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(route)
      });
    } catch (error) {
      console.error('Error buying Ticket', error);
    }
  },

  fetchTickets: async () => {
    try {
      console.log("APIWrapper: Fetching Tickets...")
      return await fetch(APIWrapper.backendURI + APIWrapper.privateAPI + 'tickets', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching Tickets', error);
    }
  },
}
export default APIWrapper;