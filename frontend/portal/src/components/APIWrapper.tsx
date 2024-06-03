
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
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'trains', { method: 'GET', credentials: 'include' });
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
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'connections', { method: 'GET', credentials: 'include' });
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
        body: JSON.stringify({ origin: origin, destination: destination, trainNumber: trainNumber, departureTime: departureTime, arrivalTime: arrivalTime, lineNumber: lineNumber, price: price })
      });
    } catch (error) {
      console.error('Error adding Connection', error);
    }
  }
}
export default APIWrapper;