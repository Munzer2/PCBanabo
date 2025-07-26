import React, { useState, useRef, useEffect } from 'react'
import api from '../../api'; 


const ChatSidebar = ({ isOpen, onClose, buildContext = null, userBuilds = [], currentPage = null, userContext = null }) => {
    const [messages, setMessages] = useState([
        { from: 'system', text: 'Hi! How can I help you with your PC build today?'}
    ]);
    const [input, setInput] = useState('');
    const bottomRef = useRef(null); /// doesnt cause re-rendering issues, so we can use it to scroll to the bottom of the chat

    useEffect(() => bottomRef.current?.scrollIntoView({ behavior: 'smooth'}), [messages]);
    
    const send = async () => {
        if(!input.trim()) return;
        const mssg = { from: 'user', text: input}; 
        setMessages(m => [...m, mssg]);
        setInput('');

        try {
            // Include context in the request
            const requestData = {
                message: mssg.text,
                buildContext: buildContext || null,
                userBuilds: userBuilds ? userBuilds.slice(0, 3) : [], // Send last 3 builds for context
                currentPage: currentPage || window.location.pathname,
                userContext: userContext || null
            };

            console.log('Sending chat request with context:', requestData); // Debug log

            const { data } = await api.post('/api/chat', requestData);
            const botMssg = { from: 'bot', text: data.reply };
            setMessages(m => [...m, botMssg]);
        }
        catch (error) {
            console.error('Chat error:', error);
            setMessages(m => [...m, { from : 'bot', text : 'Sorry, something went wrong. Please try again.'}]);
        }
    };

    const handleQuickAction = (message) => {
        setInput(message);
    };

    // Quick Action Buttons
    const QuickActions = () => (
        <div className="p-3 border-t border-gray-700 bg-gray-800">
            <div className="text-xs text-gray-400 mb-2">Quick Actions:</div>
            <div className="flex flex-wrap gap-1">
                <button 
                    onClick={() => handleQuickAction("What's the best GPU for 1440p gaming under $500?")}
                    className="text-xs bg-gray-700 hover:bg-gray-600 text-gray-200 px-2 py-1 rounded"
                >
                    GPU Advice
                </button>
                <button 
                    onClick={() => handleQuickAction("Check my build compatibility")}
                    className="text-xs bg-gray-700 hover:bg-gray-600 text-gray-200 px-2 py-1 rounded"
                >
                    Compatibility
                </button>
                <button 
                    onClick={() => handleQuickAction("Optimize my build for better performance")}
                    className="text-xs bg-gray-700 hover:bg-gray-600 text-gray-200 px-2 py-1 rounded"
                >
                    Optimize
                </button>
                <button 
                    onClick={() => handleQuickAction("What PSU wattage do I need?")}
                    className="text-xs bg-gray-700 hover:bg-gray-600 text-gray-200 px-2 py-1 rounded"
                >
                    PSU Calculator
                </button>
            </div>
        </div>
    );
    
    return (
        <div className={`fixed right-0 top-0 h-full bg-gray-800 shadow-lg flex flex-col border-l border-gray-700 z-50 transition-transform duration-300 ${
            isOpen ? 'translate-x-0' : 'translate-x-full'
        } w-full lg:w-80`}>
            <header className="p-3 border-b border-gray-700 flex justify-between items-center">
                <h3 className="font-bold text-gray-100 text-sm sm:text-base">PCBanabo Assistant</h3>
                <button 
                    onClick={onClose}
                    className="p-1 rounded-full hover:bg-gray-700 text-gray-400 hover:text-gray-200 transition-colors"
                >
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                        <line x1="18" y1="6" x2="6" y2="18"></line>
                        <line x1="6" y1="6" x2="18" y2="18"></line>
                    </svg>
                </button>
            </header>
            <div className="flex-1 p-3 sm:p-4 overflow-y-auto bg-gray-900">
                {messages.map((m,i) => (
                    <div key={i} className={`${m.from==='user'?'text-right':'text-left'} my-2`}>
                        <span className={`inline-block p-2 rounded-lg max-w-xs break-words text-sm ${
                            m.from==='user'
                                ? 'bg-purple-600 text-white'
                                : 'bg-gray-700 text-gray-100'
                        }`}>
                            {m.text}
                        </span>
                    </div>
                ))}
                <div ref={bottomRef} />
            </div>
            
            <QuickActions />
            
            <div className="p-3 border-t border-gray-700 flex bg-gray-800">
                <input
                    className="flex-1 bg-gray-700 text-gray-100 border-0 rounded-l px-3 py-2 focus:outline-none focus:ring-1 focus:ring-purple-500 text-sm"
                    value={input}
                    placeholder="Type your message..."
                    onChange={e => setInput(e.target.value)}
                    onKeyDown={e => e.key==='Enter' && send()}
                />
                <button
                    className="px-3 sm:px-4 bg-purple-600 hover:bg-purple-700 text-white rounded-r transition-colors text-sm"
                    onClick={send}
                >
                    Send
                </button>
            </div>
        </div>
    );
}

export default ChatSidebar
