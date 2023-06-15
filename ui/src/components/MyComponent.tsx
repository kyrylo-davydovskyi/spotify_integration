import React, { useState } from 'react';
import axios from 'axios';
import InputBox from './InputBox';
import Button from './Button';
import ChartComponent from './ChartComponent';
import { SongDTO } from './types';
import './styles.css'

const MyComponent: React.FC = () => {
  const [ playlists, setPlayLists ] = useState('');
  const [ clusterSize, setClusterSize ] = useState(7);
  const [ clusters, setClusters ] = useState<Record<string, SongDTO[]> | null>(null);

  const handleApiCall = async (url: string) => {
    try {
      const response = await axios.post(url, {
        playlists: playlists.split(','),
        numberOfClusters: clusterSize
      }, {
        headers: {
          'Access-Control-Allow-Origin': 'http://localhost:8085' // Replace with your actual domain
        }
      });

      setClusters(response.data);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div className={'container'}>
      <div className="sidePanel w-fit">
        <InputBox label="Playlist IDs" value={playlists} onChange={setPlayLists} />
        <InputBox
          label="Cluster Size" value={clusterSize.toString()}
          onChange={(value) => setClusterSize(Number(value))}
        />
        <div className="buttonsArea">
          <Button label="K-Means" onClick={() => handleApiCall('http://localhost:8085/playlists/kmeans')} />
          <Button label="X-Means" onClick={() => handleApiCall('http://localhost:8085/playlists/xmeans')} />
          <Button label="G-Means" onClick={() => handleApiCall('http://localhost:8085/playlists/gmeans')} />
        </div>
      </div>
      <div className="dataArea">
        <ChartComponent clusters={clusters} />
      </div>
    </div>

  );
};

export default MyComponent;
