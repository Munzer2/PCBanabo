import React, { useState, useEffect } from 'react';
import { X, ArrowLeft, Zap, Cpu, Gamepad2, Video, Camera, Star } from 'lucide-react';
import api from '../../api';

const BenchmarkModal = ({ build, onClose, onBack }) => {
    const [benchmarkData, setBenchmarkData] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchBenchmarkData = async () => {
            if (!build?.id) {
                setError('Build ID not found');
                setIsLoading(false);
                return;
            }

            try {
                setIsLoading(true);
                const response = await api.get(`/api/benchmarks/${build.id}`);
                setBenchmarkData(response.data);
                setError(null);
            } catch (error) {
                console.error('Error fetching benchmark data:', error);
                setError('Failed to load benchmark data. This build may not have benchmark scores available.');
            } finally {
                setIsLoading(false);
            }
        };

        fetchBenchmarkData();
    }, [build?.id]);

    if (!build) return null;

    const benchmarkCategories = [
        {
            title: 'CPU Performance',
            icon: <Cpu className="h-5 w-5" />,
            color: 'from-blue-600 to-blue-700',
            tests: [
                { name: 'Cinebench Single', key: 'cinebenchSingle', unit: 'pts' },
                { name: 'Cinebench Multi', key: 'cinebenchMulti', unit: 'pts' },
                { name: 'Geekbench', key: 'geekbench', unit: 'pts' }
            ]
        },
        {
            title: 'GPU Performance',
            icon: <Zap className="h-5 w-5" />,
            color: 'from-green-600 to-green-700',
            tests: [
                { name: 'Blender', key: 'blender', unit: 's' }
            ]
        },
        {
            title: 'Creative Apps',
            icon: <Camera className="h-5 w-5" />,
            color: 'from-purple-600 to-purple-700',
            tests: [
                { name: 'Photoshop', key: 'photoshop', unit: 'pts' },
                { name: 'Premiere Pro', key: 'premierePro', unit: 'pts' },
                { name: 'Lightroom', key: 'lightroom', unit: 'pts' },
                { name: 'DaVinci Resolve', key: 'davinci', unit: 'pts' }
            ]
        },
        {
            title: 'Gaming Performance',
            icon: <Gamepad2 className="h-5 w-5" />,
            color: 'from-red-600 to-red-700',
            tests: [
                { name: 'Horizon Zero Dawn', key: 'horizonZeroDawn', unit: 'fps' },
                { name: 'F1 2024', key: 'f12024', unit: 'fps' },
                { name: 'Valorant', key: 'valorant', unit: 'fps' },
                { name: 'Overwatch', key: 'overwatch', unit: 'fps' },
                { name: 'CS:GO', key: 'csgo', unit: 'fps' },
                { name: 'FC 2025', key: 'fc2025', unit: 'fps' },
                { name: 'Black Myth: Wukong', key: 'blackMythWukong', unit: 'fps' }
            ]
        }
    ];

    const getScoreColor = (score, category) => {
        if (!score || score === 0) return 'text-gray-400';
        
        // Different thresholds for different categories
        switch (category) {
            case 'Gaming Performance':
                if (score >= 120) return 'text-green-400';
                if (score >= 60) return 'text-yellow-400';
                return 'text-red-400';
            case 'CPU Performance':
                if (score >= 20000) return 'text-green-400';
                if (score >= 10000) return 'text-yellow-400';
                return 'text-red-400';
            case 'Creative Apps':
                if (score >= 8000) return 'text-green-400';
                if (score >= 5000) return 'text-yellow-400';
                return 'text-red-400';
            case 'GPU Performance':
                if (score <= 60) return 'text-green-400'; // Lower is better for render times
                if (score <= 120) return 'text-yellow-400';
                return 'text-red-400';
            default:
                return 'text-gray-300';
        }
    };

    const getPerformanceRating = (scores) => {
        if (!scores || scores.length === 0) return 'N/A';
        
        const validScores = scores.filter(score => score && score > 0);
        if (validScores.length === 0) return 'N/A';
        
        const avgScore = validScores.reduce((a, b) => a + b, 0) / validScores.length;
        
        if (avgScore >= 8000) return { rating: 'Excellent', color: 'text-green-400', stars: 5 };
        if (avgScore >= 5000) return { rating: 'Very Good', color: 'text-blue-400', stars: 4 };
        if (avgScore >= 3000) return { rating: 'Good', color: 'text-yellow-400', stars: 3 };
        if (avgScore >= 1000) return { rating: 'Fair', color: 'text-orange-400', stars: 2 };
        return { rating: 'Entry Level', color: 'text-red-400', stars: 1 };
    };

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-[60] p-4">
            <div className="bg-gray-800 rounded-xl max-w-4xl w-full max-h-[90vh] overflow-y-auto">
                {/* Header */}
                <div className="flex justify-between items-center p-6 border-b border-gray-700">
                    <div className="flex items-center gap-3">
                        <button
                            onClick={onBack}
                            className="text-gray-400 hover:text-gray-200 transition-colors"
                            title="Back to build details"
                        >
                            <ArrowLeft className="h-6 w-6" />
                        </button>
                        <div>
                            <h2 className="text-xl font-semibold text-gray-100">
                                Benchmark Scores
                            </h2>
                            <p className="text-sm text-gray-400">
                                {build.buildName}
                            </p>
                        </div>
                    </div>
                    <button
                        onClick={onClose}
                        className="text-gray-400 hover:text-gray-200 transition-colors"
                    >
                        <X className="h-6 w-6" />
                    </button>
                </div>

                {/* Content */}
                <div className="p-6">
                    {isLoading ? (
                        <div className="flex flex-col items-center justify-center py-12">
                            <div className="w-8 h-8 border-4 border-gray-600 border-t-purple-400 rounded-full animate-spin mb-4"></div>
                            <p className="text-gray-400">Loading benchmark scores...</p>
                        </div>
                    ) : error ? (
                        <div className="flex flex-col items-center justify-center py-12 text-center">
                            <div className="text-4xl mb-4">⚠️</div>
                            <h3 className="text-lg font-semibold text-yellow-400 mb-2">Benchmark Data Unavailable</h3>
                            <p className="text-gray-400 max-w-md">{error}</p>
                        </div>
                    ) : benchmarkData ? (
                        <div className="space-y-6">
                            {/* Overall Performance Summary */}
                            <div className="bg-gradient-to-r from-gray-700 to-gray-600 rounded-xl p-6">
                                <div className="flex items-center justify-between">
                                    <div>
                                        <h3 className="text-lg font-semibold text-gray-100 mb-2">
                                            Overall Performance Rating
                                        </h3>
                                        {(() => {
                                            const allScores = [
                                                benchmarkData.cinebenchSingle,
                                                benchmarkData.cinebenchMulti,
                                                benchmarkData.geekbench,
                                                benchmarkData.photoshop,
                                                benchmarkData.premierePro
                                            ];
                                            const rating = getPerformanceRating(allScores);
                                            return (
                                                <div className="flex items-center gap-2">
                                                    <span className={`text-xl font-bold ${rating.color}`}>
                                                        {rating.rating}
                                                    </span>
                                                    <div className="flex">
                                                        {[...Array(5)].map((_, i) => (
                                                            <Star
                                                                key={i}
                                                                className={`h-4 w-4 ${
                                                                    i < rating.stars
                                                                        ? 'text-yellow-400 fill-yellow-400'
                                                                        : 'text-gray-500'
                                                                }`}
                                                            />
                                                        ))}
                                                    </div>
                                                </div>
                                            );
                                        })()}
                                    </div>
                                    <div className="text-right">
                                        <p className="text-sm text-gray-400">Build Configuration</p>
                                        <p className="text-gray-200">
                                            CPU ID: {benchmarkData.cpuId} | GPU ID: {benchmarkData.gpuId}
                                        </p>
                                    </div>
                                </div>
                            </div>

                            {/* Benchmark Categories */}
                            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                                {benchmarkCategories.map((category) => (
                                    <div key={category.title} className="bg-gray-700 rounded-xl p-6">
                                        <div className={`flex items-center gap-3 mb-4 p-3 rounded-lg bg-gradient-to-r ${category.color}`}>
                                            {category.icon}
                                            <h3 className="text-lg font-semibold text-white">
                                                {category.title}
                                            </h3>
                                        </div>
                                        
                                        <div className="space-y-3">
                                            {category.tests.map((test) => {
                                                const score = benchmarkData[test.key];
                                                const scoreColor = getScoreColor(score, category.title);
                                                
                                                return (
                                                    <div key={test.key} className="flex justify-between items-center p-3 bg-gray-600 rounded-lg">
                                                        <span className="text-gray-200 font-medium">
                                                            {test.name}
                                                        </span>
                                                        <span className={`font-bold ${scoreColor}`}>
                                                            {score && score > 0 ? `${score} ${test.unit}` : 'N/A'}
                                                        </span>
                                                    </div>
                                                );
                                            })}
                                        </div>
                                    </div>
                                ))}
                            </div>

                            {/* Performance Notes */}
                            <div className="bg-gray-700 rounded-xl p-6">
                                <h3 className="text-lg font-semibold text-gray-100 mb-3">
                                    📊 Performance Notes
                                </h3>
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm">
                                    <div className="space-y-1">
                                        <p className="text-green-400">• Higher scores = Better performance</p>
                                        <p className="text-gray-300">• Cinebench: CPU rendering performance</p>
                                        <p className="text-gray-300">• Gaming: Average FPS at 1080p High settings</p>
                                    </div>
                                    <div className="space-y-1">
                                        <p className="text-yellow-400">• Blender: Lower render time = Better</p>
                                        <p className="text-gray-300">• Creative Apps: Professional workload scores</p>
                                        <p className="text-gray-300">• Results based on combined CPU+GPU performance</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    ) : (
                        <div className="flex flex-col items-center justify-center py-12">
                            <div className="text-4xl mb-4">📊</div>
                            <h3 className="text-lg font-semibold text-gray-100 mb-2">No Benchmark Data</h3>
                            <p className="text-gray-400">No benchmark scores found for this build configuration.</p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default BenchmarkModal;
