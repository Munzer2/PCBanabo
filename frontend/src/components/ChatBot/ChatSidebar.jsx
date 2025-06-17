import React, { useState, useRef, useEffect } from 'react'
import api from '../../api'; 


const ChatSidebar = ({ onClose }) => {
    const [messages, setMessages] = useState([
        { from: 'system', text: 'Hi! How can I help you today ?'}
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
            const { data } = await api.post('/api/chat', { message : mssg.text});
            const botMssg = { from: 'bot', text: data.reply };
            setMessages(m => [...m, botMssg]);
        }
        catch (error) {
            setMessages(m => [...m, { from : 'bot', text : 'Sorry, something went wrong.'}]);
        }
    };  
    
    return (
  <div className="fixed right-0 top-0 h-full w-80 bg-gray-800 shadow-lg flex flex-col border-l border-gray-700">
    <header className="p-3 border-b border-gray-700 flex justify-between items-center">
      <h3 className="font-bold text-gray-100">PCBanabo Assistant</h3>
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
    <div className="flex-1 p-4 overflow-y-auto bg-gray-900">
      {messages.map((m,i) => (
        <div key={i} className={`${m.from==='user'?'text-right':'text-left'} my-2`}>
          <span className={`inline-block p-2 rounded-lg ${
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
    <div className="p-3 border-t border-gray-700 flex bg-gray-800">
      <input
        className="flex-1 bg-gray-700 text-gray-100 border-0 rounded-l px-3 py-2 focus:outline-none focus:ring-1 focus:ring-purple-500"
        value={input}
        placeholder="Type your message..."
        onChange={e => setInput(e.target.value)}
        onKeyDown={e => e.key==='Enter' && send()}
      />
      <button
        className="px-4 bg-purple-600 hover:bg-purple-700 text-white rounded-r transition-colors"
        onClick={send}
      >
        Send
      </button>
    </div>
  </div>
);
}

export default ChatSidebar
