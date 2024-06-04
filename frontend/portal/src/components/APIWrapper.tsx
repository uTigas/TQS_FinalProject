import { Connection, Route } from "../support/Variables";

const APIWrapper = {
  //backendURI: "/", 
  backendURI: "http://localhost:8080/", 
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
      console.log("APIWrapper: Add Station...")

      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'stations', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ 'name': stationName, 'numberOfLines': stationLines })
      });
    } catch (error) {
      console.error('Error adding Station', error);
    }
  },

  fetchTrainList: async () => {
    try {
      console.log("APIWrapper: Fetching Trains...")
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