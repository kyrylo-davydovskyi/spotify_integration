import React, { useEffect, useRef } from 'react';
import { Chart, ChartData, ChartOptions, TooltipItem, registerables } from 'chart.js';
import { SongDTO } from './types';
interface ChartComponentProps {
    clusters: Record<string, SongDTO[]> | null;
}



const ChartComponent: React.FC<ChartComponentProps> = ({ clusters }) => {
    const chartRef = useRef<HTMLCanvasElement>(null);
    Chart.register(...registerables);

    useEffect(() => {
        
        if (chartRef.current && clusters) {
            const chartData: ChartData = {
                labels: [],
                datasets: []
            };

            const clusterLabels = Object.keys(clusters);

            // Generate random colors for each cluster
            const clusterColors: string[] = generateClusterColors(clusterLabels.length);

            // Iterate over each cluster
            clusterLabels.forEach((clusterLabel, clusterIndex) => {
                const songs = clusters[clusterLabel];

                const dataPoints = songs.map((song) => ({
                    x: song.popularityOfSong,
                    y: song.durationInMs,
                    artist: song.artistNames,
                    songName: song.songName
                }));

                chartData.labels!.push(clusterLabel);
                chartData.datasets.push({
                    label: clusterLabel,
                    data: dataPoints,
                    backgroundColor: clusterColors[clusterIndex],
                    borderColor: clusterColors[clusterIndex],
                    borderWidth: 1
                });
            });

            const chartOptions: ChartOptions = {
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'Popularity'
                        }
                    },
                    y: {
                        title: {
                            display: true,
                            text: 'Duration (ms)'
                        }
                    }
                },
                plugins: {
                    tooltip: {
                        callbacks: {
                            label: (context: TooltipItem<any>) => {
                                const dataPoint = context.raw as any;
                                return `${dataPoint.artist} - ${dataPoint.songName}`;
                            }
                        }
                    }
                }
            };

            new Chart(chartRef.current, {
                type: 'scatter',
                data: chartData,
                options: chartOptions
            });
        }
    }, [clusters]);

    // Function to generate random colors for each cluster
    const generateClusterColors = (numClusters: number): string[] => {
        const colors: string[] = [];
        for (let i = 0; i < numClusters; i++) {
            const color = `rgba(${getRandomInt(0, 255)}, ${getRandomInt(0, 255)}, ${getRandomInt(0, 255)}, 0.6)`;
            colors.push(color);
        }
        return colors;
    };

    // Function to generate random integer between min and max (inclusive)
    const getRandomInt = (min: number, max: number): number => {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    };

    return (
        <div>
            <canvas ref={chartRef} />
        </div>
    );
};

export default ChartComponent;
