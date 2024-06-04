
const APIWrapper = {
  backendURI: "", 
  privateAPI: "private/api/v1/",
  adminAPI: "admin/api/v1/",
  authAPI: "auth/",

  fetchUserDetails: async () => {
    try {
      return await fetch(APIWrapper.backendURI + APIWrapper.authAPI + 'user', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching User details', error);
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
      return await fetch(APIWrapper.backendURI + APIWrapper.adminAPI + 'trains', { method: 'GET', credentials: 'include' });
    } catch (error) {
      console.error('Error fetching Trains', error);
    }
  },

  addTrain: async (type: string, number: number) => {
    try {
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
}
export default APIWrapper;