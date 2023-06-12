package com.example.myapplication.game;

import android.view.MotionEvent;

public class Joystick {
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float touchX = event.getX();
                float touchY = event.getY();
                handleJoystickMovement(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                // 조이스틱을 중앙으로 초기화하는 로직 추가 (옵션)
                break;
        }

        return true;
    }

    private void handleJoystickMovement(float touchX, float touchY) {
        float centerX = 9;
        float centerY = 21;

        float maxDistance = 2;

        float dx = touchX - centerX;
        float dy = touchY - centerY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > maxDistance) {
            // 조이스틱을 최대 이동 반경 내에서 제한
            dx = (dx / distance) * maxDistance;
            dy = (dy / distance) * maxDistance;
        }

        // 플레이어의 위치 업데이트

        // 나머지 게임 요소의 스크롤 처리 (예: 배경, 장애물 등)
    }


}
