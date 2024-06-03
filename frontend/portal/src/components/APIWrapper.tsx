
const APIWrapper = {
  backendURI: "http://localhost:8080/", 
  privateAPI: "private/api/v1/",
  adminAPI: "admin/api/v1/",

  fetchUserDetails: async () => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.privateAPI + 'user', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching User details', error);
    }
  },

  fetchAdminDetails: async () => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'admin', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching Admin details', error);
    }
  },

  fetchStationList: async () => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'stations', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching Stations', error);
    }
  },

  addStation: async (stationName: string, stationLines: number) => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'stations', {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: stationName, numberOfLines: stationLines })
      });
    } catch (error) {
      console.error('Error adding Station', error);
    }
  },
  
    fetchOrganizations : async (origin : string|null = null, destination : string|null = null) => {
      try{
        let originArg
        originArg = "?origin=" + origin
        return await fetch(APIWrapper.backendURI + APIWrapper.privateAPI + 'connections' + (origin ? `?origin=${origin}` : '') + (origin && destination ? `&destination=${destination}` : '') + (!origin && destination ? `?destination=${destination}` : ''),
        { method: 'GET', credentials: 'include' })
        
      } catch (error){
        console.error('Error fetching Organizations', error);
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