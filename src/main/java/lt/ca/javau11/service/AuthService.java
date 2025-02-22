package lt.ca.javau11.service;

import lt.ca.javau11.dto.LoginDto;
import lt.ca.javau11.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);
}
