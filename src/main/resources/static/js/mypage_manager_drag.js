window.addEventListener('load', () => {
    const draggables = document.querySelectorAll(".player");
    const containers = document.querySelectorAll(".players");
    const dragSensitivity = 0.5;
    let activeDraggable = null;
    draggables.forEach(draggable => {
        draggable.addEventListener("dragstart", () => {
            draggable.classList.add("dragging");
        });

        draggable.addEventListener("dragend", () => {
            draggable.classList.remove("dragging");
        });

        // 터치 이벤트 처리 추가
        draggable.addEventListener("touchstart", (e) => {
            e.preventDefault();
            draggable.classList.add("dragging");
            activeDraggable = draggable;
            activeDraggable.startX = e.touches[0].clientX;
            activeDraggable.startY = e.touches[0].clientY;
        });
    
        draggable.addEventListener("touchend", (e) => {
            e.preventDefault();
            draggable.classList.remove("dragging");
            activeDraggable = null;
            delete draggable.startX;
            delete draggable.startY;
        });
    });

    containers.forEach(container => {
        container.addEventListener("dragover", e => {
            e.preventDefault();
            const afterElement = getDragAfterElement(container, e.clientX);
            const draggable = document.querySelector(".dragging");
            if (afterElement === undefined) {
                container.appendChild(draggable);
            } else {
                container.insertBefore(draggable, afterElement);
            }
        });
        // 터치 이벤트 처리 추가
        container.addEventListener("touchmove", (e) => {
            e.preventDefault();
            if (activeDraggable) {
                const touch = e.touches[0];

                // Calculate the movement with sensitivity
                const deltaX = (touch.clientX - activeDraggable.startX) * dragSensitivity;
                const deltaY = (touch.clientY - activeDraggable.startY) * dragSensitivity;

                // 드래그 중인 요소의 위치를 조절
                activeDraggable.style.left = `${deltaX}px`;
                activeDraggable.style.top = `${deltaY}px`;

                // 현재 위치한 container 확인
                const currentContainer = document.elementFromPoint(touch.clientX, touch.clientY);
                if (currentContainer && currentContainer.classList.contains('players')) {
                    const afterElement = getDragAfterElement(currentContainer, touch.clientX);
                    if (afterElement === undefined) {
                        currentContainer.appendChild(activeDraggable);
                    } else {
                        currentContainer.insertBefore(activeDraggable, afterElement);
                    }
                }
            }
        });
    });
    
})

function getDragAfterElement(container, x) {
    const draggableElements = [
      ...container.querySelectorAll(".player:not(.dragging)"),
    ];
  
    return draggableElements.reduce(
      (closest, child) => {
        const box = child.getBoundingClientRect();
        const offset = x - box.left - box.width / 2;
        // console.log(offset);
        if (offset < 0 && offset > closest.offset) {
          return { offset: offset, element: child };
        } else {
          return closest;
        }
      },
      { offset: Number.NEGATIVE_INFINITY },
    ).element;
};