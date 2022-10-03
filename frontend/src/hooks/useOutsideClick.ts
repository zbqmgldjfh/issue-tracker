import { useEffect } from 'react';

export function useOutsideClick(
  elements: React.MutableRefObject<HTMLElement[] | null[]> | undefined,
  callback: () => void,
) {
  useEffect(() => {
    const handleMouseDown = ({ target }: any) => {
      if (!elements) {
        return;
      }
      for (const element of elements.current) {
        if (element === null || element.contains(target)) {
          return;
        }
      }
      callback();
    };

    document.addEventListener('mousedown', handleMouseDown);

    return () => {
      document.removeEventListener('mousedown', handleMouseDown);
    };
  }, [elements, callback]);
}
