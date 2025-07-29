import React from 'react';
import { AlertTriangle, CheckCircle, Info, X } from 'lucide-react';

const CustomAlert = ({ 
  isOpen, 
  onClose, 
  onConfirm, 
  title = "PCBanabo", 
  message, 
  type = "info", // "info", "warning", "error", "success", "confirm"
  confirmText = "OK",
  cancelText = "Cancel",
  showCancel = false 
}) => {
  if (!isOpen) return null;

  const getIcon = () => {
    switch (type) {
      case 'warning':
        return <AlertTriangle className="h-6 w-6 text-yellow-500" />;
      case 'error':
        return <AlertTriangle className="h-6 w-6 text-red-500" />;
      case 'success':
        return <CheckCircle className="h-6 w-6 text-green-500" />;
      case 'confirm':
        return <AlertTriangle className="h-6 w-6 text-orange-500" />;
      default:
        return <Info className="h-6 w-6 text-blue-500" />;
    }
  };

  const getColors = () => {
    switch (type) {
      case 'warning':
        return 'border-yellow-500 bg-yellow-50 dark:bg-yellow-900/20';
      case 'error':
        return 'border-red-500 bg-red-50 dark:bg-red-900/20';
      case 'success':
        return 'border-green-500 bg-green-50 dark:bg-green-900/20';
      case 'confirm':
        return 'border-orange-500 bg-orange-50 dark:bg-orange-900/20';
      default:
        return 'border-blue-500 bg-blue-50 dark:bg-blue-900/20';
    }
  };

  const handleConfirm = () => {
    if (onConfirm) onConfirm();
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
      <div className="bg-slate-800 rounded-xl max-w-md w-full shadow-2xl border border-gray-700">
        {/* Header */}
        <div className="flex items-center justify-between p-6 border-b border-gray-700">
          <div className="flex items-center space-x-3">
            {getIcon()}
            <h2 className="text-lg font-semibold text-white">{title}</h2>
          </div>
          {!showCancel && (
            <button
              onClick={onClose}
              className="text-gray-400 hover:text-gray-200 transition-colors"
            >
              <X className="h-5 w-5" />
            </button>
          )}
        </div>

        {/* Content */}
        <div className="p-6">
          <p className="text-gray-300 text-sm leading-relaxed">
            {message}
          </p>
        </div>

        {/* Actions */}
        <div className="flex justify-end space-x-3 p-6 pt-0">
          {showCancel && (
            <button
              onClick={onClose}
              className="px-4 py-2 text-sm font-medium text-gray-300 bg-gray-700 hover:bg-gray-600 rounded-lg transition-colors"
            >
              {cancelText}
            </button>
          )}
          <button
            onClick={handleConfirm}
            className={`px-4 py-2 text-sm font-medium text-white rounded-lg transition-colors ${
              type === 'error' || type === 'warning' || type === 'confirm'
                ? 'bg-red-600 hover:bg-red-700'
                : type === 'success'
                ? 'bg-green-600 hover:bg-green-700'
                : 'bg-blue-600 hover:bg-blue-700'
            }`}
          >
            {confirmText}
          </button>
        </div>
      </div>
    </div>
  );
};

export default CustomAlert;
