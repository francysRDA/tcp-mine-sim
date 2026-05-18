import socket
import time

def send_command(client_socket, command):
    start_time = time.perf_counter()
    client_socket.send(command.encode('utf-8'))
    response = client_socket.recv(1024).decode('utf-8')
    end_time = time.perf_counter()
    
    latency = (end_time - start_time) * 1000 # Convert to ms
    return response, latency

def run_client(host='127.0.0.1', port=12345, iterations=10):
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        client.connect((host, port))
        print(f"[+] Connected to server {host}:{port}")

        latencies = []
        commands = ["MOVE 10 20 30", "PING", "CHAT Hello Server!", "MOVE 15 25 35"]
        
        for i in range(iterations):
            cmd = commands[i % len(commands)]
            response, latency = send_command(client, cmd)
            latencies.append(latency)
            print(f"[*] Command: {cmd} | Response: {response} | Latency: {latency:.2f}ms")
            time.sleep(0.1) # Simulate delay between actions

        avg_latency = sum(latencies) / len(latencies)
        print(f"\n[!] Average Latency over {iterations} commands: {avg_latency:.2f}ms")
        return latencies

    except ConnectionRefusedError:
        print("[-] Could not connect to server. Is it running?")
    finally:
        client.close()

if __name__ == "__main__":
    run_client()
