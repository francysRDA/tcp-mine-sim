import socket
import threading
import time

def handle_client(client_socket, address):
    print(f"[+] New connection from {address}")
    try:
        while True:
            data = client_socket.recv(1024).decode('utf-8')
            if not data:
                break
            
            # Simulate processing time
            # time.sleep(0.01) 
            
            command = data.strip()
            print(f"[*] Received command: {command} from {address}")
            
            # Simple command handling
            if command.startswith("MOVE"):
                response = f"ACK MOVE {command.split()[-1]}"
            elif command == "PING":
                response = "PONG"
            elif command.startswith("CHAT"):
                response = "ACK CHAT"
            else:
                response = f"ACK {command}"
            
            client_socket.send(response.encode('utf-8'))
    except ConnectionResetError:
        print(f"[-] Connection reset by {address}")
    finally:
        client_socket.close()
        print(f"[-] Connection closed for {address}")

def start_server(host='127.0.0.1', port=12345):
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind((host, port))
    server.listen(5)
    print(f"[*] Server listening on {host}:{port}")

    try:
        while True:
            client, address = server.accept()
            client_handler = threading.Thread(target=handle_client, args=(client, address))
            client_handler.start()
    except KeyboardInterrupt:
        print("\n[*] Server shutting down.")
    finally:
        server.close()

if __name__ == "__main__":
    start_server()
